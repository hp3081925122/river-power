# 坚固板大水车贴图：逐像素手绘字符网格（不使用程序噪声）
# 配色取自 Create 6.0.8 的 item/sturdy_sheet：偏蓝紫的深钢灰，整套只用这一组色阶
# 用法: python paint_textures.py [输出目录]  默认写入 src/main/resources/.../textures/block
# UV 说明见 ../andesite_large_water_wheel/paint_textures.py 顶部
import os
import sys
from PIL import Image

# Create sturdy_sheet 色阶（W、g 为在其色阶上补的高光与中间档）
STURDY = {
    'W': (178, 177, 198),
    'i': (142, 141, 161),
    'h': (112, 110, 130),
    'g': (96, 95, 113),
    'f': (82, 81, 97),
    'e': (67, 66, 81),
    'd': (56, 56, 70),
    'c': (42, 42, 55),
    'b': (25, 27, 36),
    'a': (11, 13, 21),
}

# planks -> 坚固钢板：四条横向板带，每块板 8 像素长、接缝错开，板端各一颗铆钉
PANEL = [
    "hhgeihhhhhgeihhh",
    "ggfbhWggghfbhWgg",
    "ffebgcffffebgcff",
    "cccbcccccccbcccc",
    "ihhhhhgeihhhhhge",
    "hWgghgfbhWggggfb",
    "gcffffebgcffffeb",
    "cccccccbcccccccb",
    "hhgeihhhhhgeihhh",
    "ggfbhWggggfhhWgg",
    "ffebgcffffebgcff",
    "cccbcccccccbcccc",
    "ihhhhhgeihhhhhge",
    "hWggggfbhWgggghb",
    "gcffffebgcffffeb",
    "cccccccbcccccccb",
]

# log -> 轮缘内壁：深色压边 + 竖向加强筋，筋上下两端各一个铆钉孔
BEAM = [
    "bbbbbbbbbbbbbbbb",
    "dfddfddfddfddfdd",
    "cgecgecgecgecgee",
    "cgeagecgeagecgee",
    "cgecgecgecgecgee",
    "cfecfecfecfecfee",
    "cfecfecfecfecfee",
    "cfecfecfecfecfee",
    "cfecfecfecfecfee",
    "cfecfecfecfecfee",
    "cfecfecfecfecfee",
    "cgecgecgecgecgee",
    "cgeagecgeagecgee",
    "ceeceecceeceecee",
    "cccccccccccccccc",
    "bbbbbbbbbbbbbbbb",
]

# log_top -> 轮毂端盖：近黑外框、倒角钢盖、四角铆钉、中心深色轴座
HUB = [
    "aaaaaaaaaaaaaaaa",
    "abbbbbbbbbbbbbba",
    "abhiiiiiiiiiigba",
    "abiWgffffffgWeba",
    "abigccccccccgeba",
    "abifchhhhhhcfeba",
    "abifchgbbegcfeba",
    "abifchbaabgcfeba",
    "abifchbaabgcfeba",
    "abifchebbggcfeba",
    "abifcggggggcfeba",
    "abigccccccccfeba",
    "abiWgffffffgWeba",
    "abgeeeeeeeeeeeba",
    "abbbbbbbbbbbbbba",
    "aaaaaaaaaaaaaaaa",
]

# 金属件：比板面再暗两档的支架；(2,5)、(1,7) 亮点同时作为轮缘接缝螺栓
METAL = [
    "dc..............",
    "cb..............",
    "ddc.............",
    "dcb.............",
    "cba.............",
    "eeW.............",
    "edc.............",
    "dic.............",
    "cca.............",
    "edc.............",
    "ddc.............",
    "cba.............",
    "................",
    "................",
    "................",
    "................",
]


def paint(grid, pal):
    assert len(grid) == 16 and all(len(r) == 16 for r in grid), grid
    im = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
    for y, row in enumerate(grid):
        for x, c in enumerate(row):
            if c != '.':
                im.putpixel((x, y), pal[c] + (255,))
    return im


def main():
    here = os.path.dirname(os.path.abspath(__file__))
    out = sys.argv[1] if len(sys.argv) > 1 else os.path.join(
        here, '..', '..', 'src', 'main', 'resources', 'assets', 'riverpower', 'textures', 'block')
    os.makedirs(out, exist_ok=True)
    for name, grid in (('panel', PANEL), ('beam', BEAM), ('hub', HUB), ('metal', METAL)):
        paint(grid, STURDY).save(os.path.join(out, f'sturdy_water_wheel_{name}.png'))


if __name__ == '__main__':
    main()
