#!/usr/bin/env python3
import os
import shutil

PLAY_ROOT = "composeApp/src/androidMain/play"

# Clean target directory if exists
if os.path.exists(PLAY_ROOT):
    shutil.rmtree(PLAY_ROOT)

os.makedirs(PLAY_ROOT, exist_ok=True)

# 1. English (en-US)
en_dir = os.path.join(PLAY_ROOT, "listings", "en-US")
os.makedirs(os.path.join(en_dir, "graphics", "icon"), exist_ok=True)
os.makedirs(os.path.join(en_dir, "graphics", "feature-graphic"), exist_ok=True)
os.makedirs(os.path.join(en_dir, "graphics", "phone-screenshots"), exist_ok=True)

with open("playstore/metadata/listing_en.md") as f:
    text_en = f.read()

en_title = "Sabeomnim: Taekwondo Master"
en_short = "Kukkiwon WT curriculum, dual-angle Taegeuk Poomsae, audio glossary & exam prep."
en_full = text_en.split("## Full Description (Max 4,000 characters)\n\n")[1].strip()

with open(os.path.join(en_dir, "title.txt"), "w") as f:
    f.write(en_title)

with open(os.path.join(en_dir, "short-description.txt"), "w") as f:
    f.write(en_short)

with open(os.path.join(en_dir, "full-description.txt"), "w") as f:
    f.write(en_full)

# 2. Danish (da-DK)
da_dir = os.path.join(PLAY_ROOT, "listings", "da-DK")
os.makedirs(os.path.join(da_dir, "graphics", "icon"), exist_ok=True)
os.makedirs(os.path.join(da_dir, "graphics", "feature-graphic"), exist_ok=True)
os.makedirs(os.path.join(da_dir, "graphics", "phone-screenshots"), exist_ok=True)

with open("playstore/metadata/listing_da.md") as f:
    text_da = f.read()

da_title = "Sabeomnim: Taekwondo Mester"
da_short = "WT & Kukkiwon pensum, multi-vinkel Taegeuk poomsae, lydordbog og teoriprøve."
da_full = text_da.split("## Fuld beskrivelse (Maks 4.000 tegn)\n\n")[1].strip()

with open(os.path.join(da_dir, "title.txt"), "w") as f:
    f.write(da_title)

with open(os.path.join(da_dir, "short-description.txt"), "w") as f:
    f.write(da_short)

with open(os.path.join(da_dir, "full-description.txt"), "w") as f:
    f.write(da_full)

# 3. Copy Graphics (Icon, Feature Graphic, Phone Screenshots)
icon_src = "playstore/graphics/icon_512x512.png"
feature_src = "playstore/graphics/feature_graphic_1024x500.png"
showcase_dir = "playstore/screenshots/showcase"

for loc_dir in [en_dir, da_dir]:
    shutil.copy(icon_src, os.path.join(loc_dir, "graphics", "icon", "icon.png"))
    shutil.copy(feature_src, os.path.join(loc_dir, "graphics", "feature-graphic", "feature-graphic.png"))

    # Copy showcase screenshots in order 1..6
    showcase_files = sorted([f for f in os.listdir(showcase_dir) if f.endswith(".png")])
    for idx, fname in enumerate(showcase_files, start=1):
        src_path = os.path.join(showcase_dir, fname)
        dest_path = os.path.join(loc_dir, "graphics", "phone-screenshots", f"{idx}_{fname}")
        shutil.copy(src_path, dest_path)

# 4. Release Notes
rel_notes_en = os.path.join(PLAY_ROOT, "release-notes", "en-US")
rel_notes_da = os.path.join(PLAY_ROOT, "release-notes", "da-DK")
os.makedirs(rel_notes_en, exist_ok=True)
os.makedirs(rel_notes_da, exist_ok=True)

with open(os.path.join(rel_notes_en, "default.txt"), "w") as f:
    f.write("""• Initial release of Sabeomnim for Android
• Official Kukkiwon World Taekwondo curriculum (10th Geup to 1st Dan)
• Synchronized dual-angle Taegeuk Poomsae video player (Front 0° & Side 90°)
• Interactive movement pattern diagrams
• Korean audio terminology glossary with authentic native spoken pronunciation
• Belt promotion grading theory exam simulator
• Offline-first with Black Dobok Dark Mode
""")

with open(os.path.join(rel_notes_da, "default.txt"), "w") as f:
    f.write("""• Første udgivelse af Sabeomnim til Android
• Det officielle Kukkiwon World Taekwondo pensum (10. Geup til 1. Dan)
• Multi-vinkel Taegeuk poomsae videoafspiller (Front 0° og Side 90°)
• Interaktive skridtdiagrammer og mønstre
• Koreansk lydordbog med autentisk udtale
• Bæltespecifikke gradueringsteoriprøver
• Fungerer 100% offline med mørkt tema
""")

# 5. Mirror/symlink to composeApp/src/main/play for maximum compatibility
main_play = "composeApp/src/main/play"
if os.path.exists(main_play) or os.path.islink(main_play):
    if os.path.islink(main_play):
        os.unlink(main_play)
    else:
        shutil.rmtree(main_play)

os.makedirs("composeApp/src/main", exist_ok=True)
os.symlink("../androidMain/play", main_play)

print("Successfully configured Gradle Play Publisher directory structure:")
for root, dirs, files in os.walk(PLAY_ROOT):
    level = root.replace(PLAY_ROOT, '').count(os.sep)
    indent = ' ' * 4 * level
    print(f"{indent}{os.path.basename(root)}/")
    subindent = ' ' * 4 * (level + 1)
    for f in sorted(files):
        print(f"{subindent}{f}")
