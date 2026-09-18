#!/usr/bin/env python3
import os
from PIL import Image, ImageDraw, ImageFont, ImageFilter

OUTPUT_DIR = "playstore"
GRAPHICS_DIR = os.path.join(OUTPUT_DIR, "graphics")
SHOWCASE_DIR = os.path.join(OUTPUT_DIR, "screenshots", "showcase")
RAW_DIR = os.path.join(OUTPUT_DIR, "screenshots", "raw")

os.makedirs(GRAPHICS_DIR, exist_ok=True)
os.makedirs(SHOWCASE_DIR, exist_ok=True)

FONT_BOLD = "/System/Library/Fonts/Supplemental/Arial Bold.ttf"
FONT_REGULAR = "/System/Library/Fonts/Supplemental/Arial.ttf"

def create_feature_graphic():
    width, height = 1024, 500
    # Create dark base
    img = Image.new("RGB", (width, height), (18, 18, 20))
    draw = ImageDraw.Draw(img)

    # Gradient background / accent lighting
    # Draw subtle circular glows (Taegeuk Blue and Red)
    accent_layer = Image.new("RGBA", (width, height), (0, 0, 0, 0))
    accent_draw = ImageDraw.Draw(accent_layer)
    
    # Blue glow on bottom-left / center
    accent_draw.ellipse([(-100, -50), (450, 500)], fill=(0, 71, 160, 45))
    # Red glow on top-right
    accent_draw.ellipse([(600, -100), (1150, 450)], fill=(200, 16, 46, 35))
    
    # Blur the glows
    accent_layer = accent_layer.filter(ImageFilter.GaussianBlur(80))
    img.paste(accent_layer, (0, 0), accent_layer)
    draw = ImageDraw.Draw(img)

    # Subtle grid / martial line design
    for x in range(0, width, 64):
        draw.line([(x, 0), (x, height)], fill=(30, 30, 35), width=1)
    for y in range(0, height, 50):
        draw.line([(0, y), (width, y)], fill=(30, 30, 35), width=1)

    # Load and place app icon
    icon_path = os.path.join(GRAPHICS_DIR, "icon_512x512.png")
    if os.path.exists(icon_path):
        icon = Image.open(icon_path).convert("RGBA")
        icon_size = 280
        icon = icon.resize((icon_size, icon_size), Image.Resampling.LANCZOS)
        
        # Rounded corners with border for icon
        mask = Image.new("L", (icon_size, icon_size), 0)
        mask_draw = ImageDraw.Draw(mask)
        mask_draw.rounded_rectangle([(0, 0), (icon_size, icon_size)], radius=50, fill=255)
        
        icon_pos = (80, (height - icon_size) // 2)
        
        # Shadow behind icon
        shadow = Image.new("RGBA", (icon_size + 40, icon_size + 40), (0, 0, 0, 0))
        sdraw = ImageDraw.Draw(shadow)
        sdraw.rounded_rectangle([(10, 10), (icon_size + 30, icon_size + 30)], radius=54, fill=(0, 0, 0, 160))
        shadow = shadow.filter(ImageFilter.GaussianBlur(15))
        img.paste(shadow, (icon_pos[0] - 15, icon_pos[1] - 10), shadow)
        
        img.paste(icon, icon_pos, mask)

    FONT_KOREAN = "/System/Library/Fonts/Supplemental/AppleGothic.ttf"
    if not os.path.exists(FONT_KOREAN):
        FONT_KOREAN = "/System/Library/Fonts/AppleSDGothicNeo.ttc"

    # Text content on right side
    text_x = 400
    font_title = ImageFont.truetype(FONT_BOLD, 54)
    font_korean = ImageFont.truetype(FONT_KOREAN, 36)
    font_subtitle = ImageFont.truetype(FONT_REGULAR, 26)
    font_bullets = ImageFont.truetype(FONT_BOLD, 17)

    # Title "Sabeomnim"
    draw.text((text_x, 80), "Sabeomnim", font=font_title, fill=(255, 255, 255))
    
    # Korean text
    draw.text((text_x + 325, 96), "사범님", font=font_korean, fill=(200, 16, 46))

    # Subtitle
    draw.text(
        (text_x, 155),
        "World Taekwondo (WT / Kukkiwon) Master",
        font=font_subtitle,
        fill=(220, 220, 225)
    )
    draw.text(
        (text_x, 192),
        "10th Geup (White Belt) to 1st Dan (Black Belt)",
        font=font_subtitle,
        fill=(160, 165, 180)
    )

    # Feature badges / pills
    badges = [
        ("10 Geup Curriculum", (40, 45, 60)),
        ("Dual-Angle Poomsae", (0, 71, 160)),
        ("Spoken Korean Audio", (200, 16, 46)),
        ("Theory Exam Simulator", (40, 45, 60)),
    ]

    badge_y = 265
    cur_x = text_x
    for label, bg_color in badges[:2]:
        # Measure text
        bbox = font_bullets.getbbox(label)
        tw = bbox[2] - bbox[0]
        th = bbox[3] - bbox[1]
        bw, bh = tw + 28, th + 20
        draw.rounded_rectangle([(cur_x, badge_y), (cur_x + bw, badge_y + bh)], radius=12, fill=bg_color)
        draw.text((cur_x + 14, badge_y + 9), label, font=font_bullets, fill=(255, 255, 255))
        cur_x += bw + 14

    badge_y = 325
    cur_x = text_x
    for label, bg_color in badges[2:]:
        bbox = font_bullets.getbbox(label)
        tw = bbox[2] - bbox[0]
        th = bbox[3] - bbox[1]
        bw, bh = tw + 28, th + 20
        draw.rounded_rectangle([(cur_x, badge_y), (cur_x + bw, badge_y + bh)], radius=12, fill=bg_color)
        draw.text((cur_x + 14, badge_y + 9), label, font=font_bullets, fill=(255, 255, 255))
        cur_x += bw + 14

    # Bottom footer tag
    font_foot = ImageFont.truetype(FONT_REGULAR, 16)
    draw.text(
        (text_x, 405),
        "100% Offline-Ready • Zero Ads • Privacy-First",
        font=font_foot,
        fill=(130, 135, 145)
    )

    out_file = os.path.join(GRAPHICS_DIR, "feature_graphic_1024x500.png")
    img.save(out_file, format="PNG")
    print(f"Created feature graphic: {out_file} (1024x500 RGB)")


def create_showcase_screenshots():
    # Target dimension: 1080 x 2424 (Standard Pixel 9 / Modern phone 20:9 ratio)
    W, H = 1080, 2424
    
    screens = [
        {
            "raw": "01_curriculum.png",
            "out": "01_showcase_curriculum.png",
            "tag": "OFFICIAL KUKKIWON CURRICULUM",
            "headline": "Master Every Belt Grade",
            "sub": "Complete 10th Geup to 1st Dan requirements, stances & techniques",
            "accent": (230, 140, 20) # Orange/Gold
        },
        {
            "raw": "02_poomsae_player.png",
            "out": "02_showcase_poomsae_player.png",
            "tag": "MULTI-ANGLE VIDEO PLAYER",
            "headline": "Synchronized Dual Camera Views",
            "sub": "Switch between Front (0°) & Side (90°) angles with step subtitles",
            "accent": (0, 130, 255) # Blue
        },
        {
            "raw": "03_movement_diagram.png",
            "out": "03_showcase_movement_diagrams.png",
            "tag": "FORM CHEAT SHEETS & DIAGRAMS",
            "headline": "Official Movement Patterns",
            "sub": "Interactive diagrams, Kihap markers & step-by-step breakdown",
            "accent": (50, 180, 120) # Green
        },
        {
            "raw": "04_glossary.png",
            "out": "04_showcase_audio_glossary.png",
            "tag": "KOREAN TERMINOLOGY & AUDIO",
            "headline": "Native Spoken Pronunciation",
            "sub": "160+ martial arts terms, commands & stances with authentic audio",
            "accent": (235, 60, 80) # Red
        },
        {
            "raw": "05_quiz.png",
            "out": "05_showcase_theory_exam.png",
            "tag": "EXAM READINESS SIMULATOR",
            "headline": "Belt Promotion Grading Quizzes",
            "sub": "Official theory question pool from White Belt to Black Belt",
            "accent": (160, 80, 240) # Purple
        },
        {
            "raw": "06_settings.png",
            "out": "06_showcase_voice_and_themes.png",
            "tag": "CUSTOMIZABLE EXPERIENCE",
            "headline": "Sabeomnim Voice & Dark Mode",
            "sub": "Deep baritone male instructor voice, Danish & English localization",
            "accent": (100, 150, 255) # Light Blue
        }
    ]

    font_tag = ImageFont.truetype(FONT_BOLD, 30)
    font_head = ImageFont.truetype(FONT_BOLD, 62)
    font_sub = ImageFont.truetype(FONT_REGULAR, 34)

    for item in screens:
        raw_path = os.path.join(RAW_DIR, item["raw"])
        if not os.path.exists(raw_path):
            print(f"Skipping missing: {raw_path}")
            continue

        canvas = Image.new("RGB", (W, H), (14, 15, 18))
        draw = ImageDraw.Draw(canvas)

        # Subtle top accent gradient
        accent_color = item["accent"]
        accent_layer = Image.new("RGBA", (W, H), (0, 0, 0, 0))
        adraw = ImageDraw.Draw(accent_layer)
        adraw.ellipse([(W//2 - 400, -250), (W//2 + 400, 350)], fill=(*accent_color, 40))
        accent_layer = accent_layer.filter(ImageFilter.GaussianBlur(100))
        canvas.paste(accent_layer, (0, 0), accent_layer)
        draw = ImageDraw.Draw(canvas)

        # Text Header Section (Top 420px)
        tag_text = item["tag"]
        tag_bbox = font_tag.getbbox(tag_text)
        tag_w = tag_bbox[2] - tag_bbox[0]
        draw.text(((W - tag_w) // 2, 80), tag_text, font=font_tag, fill=accent_color)

        head_text = item["headline"]
        head_bbox = font_head.getbbox(head_text)
        head_w = head_bbox[2] - head_bbox[0]
        draw.text(((W - head_w) // 2, 135), head_text, font=font_head, fill=(255, 255, 255))

        sub_text = item["sub"]
        sub_bbox = font_sub.getbbox(sub_text)
        sub_w = sub_bbox[2] - sub_bbox[0]
        draw.text(((W - sub_w) // 2, 220), sub_text, font=font_sub, fill=(180, 185, 195))

        # Thin separator line
        draw.line([(80, 290), (W - 80, 290)], fill=(40, 42, 48), width=2)

        # Mockup phone frame & screenshot
        # Phone mockup top at y = 330
        phone_top = 330
        phone_w = 960
        phone_h = 2050
        phone_x = (W - phone_w) // 2

        # Draw phone outer chassis (Dark titanium bezel)
        draw.rounded_rectangle(
            [(phone_x, phone_top), (phone_x + phone_w, phone_top + phone_h)],
            radius=48,
            fill=(26, 28, 32),
            outline=(55, 58, 66),
            width=3
        )

        # Load raw screenshot and fit inside phone screen
        raw_img = Image.open(raw_path).convert("RGBA")
        inner_w = phone_w - 24
        inner_h = phone_h - 24
        inner_x = phone_x + 12
        inner_y = phone_top + 12

        # Scale raw image maintaining aspect ratio
        screen_aspect = raw_img.width / raw_img.height
        target_h = int(inner_w / screen_aspect)
        scaled_img = raw_img.resize((inner_w, target_h), Image.Resampling.LANCZOS)

        # Create rounded mask for screenshot
        screen_mask = Image.new("L", (inner_w, inner_h), 0)
        smask_draw = ImageDraw.Draw(screen_mask)
        smask_draw.rounded_rectangle([(0, 0), (inner_w, inner_h)], radius=36, fill=255)

        # Crop if needed or paste
        cropped_screen = scaled_img.crop((0, 0, inner_w, inner_h))
        canvas.paste(cropped_screen, (inner_x, inner_y), screen_mask)

        out_path = os.path.join(SHOWCASE_DIR, item["out"])
        canvas.save(out_path, format="PNG")
        print(f"Created showcase screenshot: {out_path} ({W}x{H})")

if __name__ == "__main__":
    create_feature_graphic()
    create_showcase_screenshots()
