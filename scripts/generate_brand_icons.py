"""Regenerate Towfik Music artwork: pip install resvg-py pillow, then run this file."""
from pathlib import Path
from io import BytesIO
import resvg_py
from PIL import Image

ROOT = Path(__file__).resolve().parents[1]
RES = ROOT / 'app/src/main/res'
# Music-note stem doubles as a T; the cyan play triangle completes the mark.
paths = [
    ('#C4B5FD', 'M34 32 Q30 32 30 36 Q30 40 34 40 H49 V65 C45 63 36 65 36 72 C36 80 57 81 57 70 V40 H71 Q75 40 75 36 Q75 32 71 32 Z'),
    ('#67E8F9', 'M65 49 Q63 48 63 51 V69 Q63 72 66 70 L79 62 Q82 60 79 58 Z'),
]

def vector(background=False, monochrome=False):
    body = '<path android:fillColor="#121225" android:pathData="M0 0 H108 V108 H0 Z"/>' if background else ''
    for color, path in paths:
        body += f'<path android:fillColor="{"#FFFFFF" if monochrome else color}" android:pathData="{path}"/>\n'
    return f'<vector xmlns:android="http://schemas.android.com/apk/res/android" android:width="108dp" android:height="108dp" android:viewportWidth="108" android:viewportHeight="108">{body}</vector>\n'

for name, bg, mono in [('ic_towfik_logo',True,False), ('ic_towfik_foreground',False,False), ('ic_towfik_monochrome',False,True), ('ic_velune_concept',False,False)]:
    (RES / f'drawable/{name}.xml').write_text(vector(bg,mono))

def svg(background=True, round_icon=False, mono=False):
    bg = f'<rect width="108" height="108" rx="{54 if round_icon else 24}" fill="#121225"/>' if background else ''
    return '<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 108 108">' + bg + ''.join(f'<path fill="{"#FFFFFF" if mono else color}" d="{path}"/>' for color,path in paths) + '</svg>'

(ROOT / 'assets/towfik-music-icon.svg').write_text(svg())
for density, size in [('mdpi',48),('hdpi',72),('xhdpi',96),('xxhdpi',144),('xxxhdpi',192)]:
    folder=RES / f'mipmap-{density}'
    for name, bg, rounded, mono in [('ic_launcher',True,False,False),('ic_launcher_round',True,True,False),('ic_launcher_foreground',False,False,False),('ic_launcher_monochrome',False,False,True)]:
        ext='png' if mono else 'webp'
        png=resvg_py.svg_to_bytes(svg_string=svg(bg,rounded,mono),width=size,height=size)
        Image.open(BytesIO(png)).save(folder / f'{name}.{ext}')
    Image.new('RGB',(size,size),'#121225').save(folder/'ic_launcher_background.png')
(ROOT/'fastlane/metadata/android/en-US/images/icon.png').write_bytes(resvg_py.svg_to_bytes(svg_string=svg(),width=512,height=512))
