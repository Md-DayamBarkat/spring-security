import argparse
import re
import sys
from pathlib import Path
from urllib.parse import parse_qs, urlparse

try:
    from youtube_transcript_api import YouTubeTranscriptApi
except ImportError:
    print("Missing dependency: youtube-transcript-api", file=sys.stderr)
    print("Install it with: python -m pip install youtube-transcript-api", file=sys.stderr)
    sys.exit(1)


def extract_video_id(value):
    value = value.strip()

    if re.fullmatch(r"[A-Za-z0-9_-]{11}", value):
        return value

    parsed = urlparse(value)

    if parsed.netloc in {"youtu.be", "www.youtu.be"}:
        candidate = parsed.path.lstrip("/").split("/")[0]
        if re.fullmatch(r"[A-Za-z0-9_-]{11}", candidate):
            return candidate

    if "youtube.com" in parsed.netloc or "youtube-nocookie.com" in parsed.netloc:
        query_video_id = parse_qs(parsed.query).get("v", [None])[0]
        if query_video_id and re.fullmatch(r"[A-Za-z0-9_-]{11}", query_video_id):
            return query_video_id

        path_parts = [part for part in parsed.path.split("/") if part]
        for marker in ("embed", "shorts", "live"):
            if marker in path_parts:
                index = path_parts.index(marker)
                if index + 1 < len(path_parts):
                    candidate = path_parts[index + 1]
                    if re.fullmatch(r"[A-Za-z0-9_-]{11}", candidate):
                        return candidate

    raise ValueError("Could not extract a valid YouTube video ID from the input.")


def seconds_to_timestamp(seconds):
    total_seconds = int(seconds)
    hours = total_seconds // 3600
    minutes = (total_seconds % 3600) // 60
    secs = total_seconds % 60

    if hours:
        return f"{hours:02d}:{minutes:02d}:{secs:02d}"
    return f"{minutes:02d}:{secs:02d}"


def build_text(transcript, with_timestamps):
    lines = []
    for item in transcript:
        text = item.text.replace("\n", " ").strip()
        if not text:
            continue
        if with_timestamps:
            lines.append(f"{seconds_to_timestamp(item.start)}  {text}")
        else:
            lines.append(text)
    return "\n".join(lines)


def main():
    parser = argparse.ArgumentParser(
        description="Fetch a YouTube transcript from a full URL or video ID."
    )
    parser.add_argument("video", help="YouTube URL or 11-character video ID")
    parser.add_argument(
        "--language",
        default="en",
        help="Preferred transcript language code. Default: en",
    )
    parser.add_argument(
        "--timestamps",
        action="store_true",
        help="Include timestamps in the output",
    )
    parser.add_argument(
        "--output",
        help="Optional file path to save the transcript",
    )
    parser.add_argument(
        "--limit",
        type=int,
        help="Print only the first N transcript lines",
    )

    args = parser.parse_args()

    try:
        video_id = extract_video_id(args.video)
    except ValueError as ex:
        print(str(ex), file=sys.stderr)
        sys.exit(2)

    try:
        transcript = YouTubeTranscriptApi().fetch(video_id, languages=[args.language])
    except Exception as ex:
        print(f"Failed to fetch transcript: {ex}", file=sys.stderr)
        sys.exit(3)

    items = list(transcript)
    if args.limit is not None:
        items = items[: args.limit]

    output_text = build_text(items, args.timestamps)

    if args.output:
        output_path = Path(args.output)
        output_path.parent.mkdir(parents=True, exist_ok=True)
        output_path.write_text(output_text, encoding="utf-8")
        print(f"Saved transcript to: {output_path}")
        return

    print(output_text)


if __name__ == "__main__":
    main()
