#!/usr/bin/env python3
import os
import shutil

PLAY_ROOT = "composeApp/src/androidMain/play"

# Clean target directory if exists
if os.path.exists(PLAY_ROOT):
    shutil.rmtree(PLAY_ROOT)

os.makedirs(PLAY_ROOT, exist_ok=True)

# 0. App Details & Default Language
with open(os.path.join(PLAY_ROOT, "default-language.txt"), "w") as f:
    f.write("en-GB\n")

with open(os.path.join(PLAY_ROOT, "contact-email.txt"), "w") as f:
    f.write("slott.hansen@gmail.com\n")

with open(os.path.join(PLAY_ROOT, "contact-website.txt"), "w") as f:
    f.write("https://github.com/slott-hansen/sabeomnim\n")

# Load text sources
with open("playstore/metadata/listing_en.md") as f:
    text_en = f.read()

en_title = "Sabeomnim: Taekwondo Master"
en_short = "Kukkiwon WT curriculum, dual-angle Taegeuk Poomsae, audio glossary & exam prep."
en_full = text_en.split("## Full Description (Max 4,000 characters)\n\n")[1].strip()

with open("playstore/metadata/listing_da.md") as f:
    text_da = f.read()

da_title = "Sabeomnim: Taekwondo Mester"
da_short = "WT & Kukkiwon pensum, multi-vinkel Taegeuk poomsae, lydordbog og teoriprøve."
da_full = text_da.split("## Fuld beskrivelse (Maks 4.000 tegn)\n\n")[1].strip()

# Setup locales: en-GB (default), en-US, da-DK
locales = {
    "en-GB": (en_title, en_short, en_full),
    "en-US": (en_title, en_short, en_full),
    "da-DK": (da_title, da_short, da_full),
}

icon_src = "playstore/graphics/icon_512x512.png"
feature_src = "playstore/graphics/feature_graphic_1024x500.png"
showcase_dir = "playstore/screenshots/showcase"
showcase_files = sorted([f for f in os.listdir(showcase_dir) if f.endswith(".png")])

for loc, (title, short, full) in locales.items():
    loc_dir = os.path.join(PLAY_ROOT, "listings", loc)
    os.makedirs(os.path.join(loc_dir, "graphics", "icon"), exist_ok=True)
    os.makedirs(os.path.join(loc_dir, "graphics", "feature-graphic"), exist_ok=True)
    os.makedirs(os.path.join(loc_dir, "graphics", "phone-screenshots"), exist_ok=True)

    with open(os.path.join(loc_dir, "title.txt"), "w") as f:
        f.write(title)
    with open(os.path.join(loc_dir, "short-description.txt"), "w") as f:
        f.write(short)
    with open(os.path.join(loc_dir, "full-description.txt"), "w") as f:
        f.write(full)

    # Graphics
    shutil.copy(icon_src, os.path.join(loc_dir, "graphics", "icon", "icon.png"))
    shutil.copy(feature_src, os.path.join(loc_dir, "graphics", "feature-graphic", "feature-graphic.png"))

    for idx, fname in enumerate(showcase_files, start=1):
        src_path = os.path.join(showcase_dir, fname)
        dest_path = os.path.join(loc_dir, "graphics", "phone-screenshots", f"{idx}.png")
        shutil.copy(src_path, dest_path)

# Release Notes
rel_notes_en = """• Initial release of Sabeomnim for Android
• Official Kukkiwon World Taekwondo curriculum (10th Geup to 1st Dan)
• Synchronized dual-angle Taegeuk Poomsae video player (Front 0° & Side 90°)
• Interactive movement pattern diagrams
• Korean audio terminology glossary with authentic native spoken pronunciation
• Belt promotion grading theory exam simulator
• Offline-first with Black Dobok Dark Mode
"""

rel_notes_da = """• Første udgivelse af Sabeomnim til Android
• Det officielle Kukkiwon World Taekwondo pensum (10. Geup til 1. Dan)
• Multi-vinkel Taegeuk poomsae videoafspiller (Front 0° og Side 90°)
• Interaktive skridtdiagrammer og mønstre
• Koreansk lydordbog med autentisk udtale
• Bæltespecifikke gradueringsteoriprøver
• Fungerer 100% offline med mørkt tema
"""

for loc, notes in [("en-GB", rel_notes_en), ("en-US", rel_notes_en), ("da-DK", rel_notes_da)]:
    n_dir = os.path.join(PLAY_ROOT, "release-notes", loc)
    os.makedirs(n_dir, exist_ok=True)
    with open(os.path.join(n_dir, "default.txt"), "w") as f:
        f.write(notes)

# Mirror / symlink to composeApp/src/main/play
main_play = "composeApp/src/main/play"
if os.path.exists(main_play) or os.path.islink(main_play):
    if os.path.islink(main_play):
        os.unlink(main_play)
    else:
        shutil.rmtree(main_play)

os.makedirs("composeApp/src/main", exist_ok=True)
os.symlink("../androidMain/play", main_play)

print("Setup completed successfully.")
