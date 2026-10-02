# 黄铜大水车贴图 v2：逐像素手绘字符网格（不使用程序噪声）
# 风格对齐 Create 黄铜机壳：深色木板 + 抛光黄铜件（支架、轮毂、内壁、钉头）
# 用法: python paint_textures.py [输出目录]  默认写入 src/main/resources/.../textures/block
# UV 说明见 ../andesite_large_water_wheel/paint_textures.py 顶部
import os
import sys
from PIL import Image

# Create brass_block 色阶
BRASS = {
    'w': (255, 235, 140),
    'h': (247, 203, 108),
    'l': (228, 183, 99),
    'o': (215, 170, 94),
    'n': (206, 160, 90),
    'm': (191, 142, 85),
    'd': (158, 105, 71),
    '#': (114, 71, 49),
    'X': (89, 36, 36),
}

# 黄铜机壳一系的暖色深木，比机壳内木再亮一档，避免整只水车发闷
# N/S = 黄铜钉头与钉影
WOOD = {
    'h': (146, 99, 55),
    'l': (127, 85, 47),
    'n': (110, 72, 40),
    'm': (94, 61, 34),
    'd': (78, 49, 28),
    'x': (61, 37, 22),
    'k': (45, 27, 17),
    'N': BRASS['h'],
    'S': (45, 27, 17),
}

# planks -> 深色木板：四条横板，顶边受光、底边暗缝、竖缝错开；每块板端一颗黄铜钉
PANEL = [
    "llllllllllnxhlll",
    "nnllllnnnnmklNnn",
    "mmmmmddddmdknSmm",
    "xxxxxxxxxxxkxxxx",
    "llnxhlllllllllll",
    "nnmklNnnllllnnnn",
    "mmdknSmmmmmmdddm",
    "xxxkxxxxxxxxxxxx",
    "llllllllllllnxhl",
    "nnnnllllnnnnmklN",
    "ddmmmmmdddmmdknS",
    "xxxxxxxxxxxxxkxx",
    "lllllnxhllllllll",
    "nnnnnmklNnllllnn",
    "mdddmdknSmmmmmmm",
    "xxxxxxkxxxxxxxxx",
]

# log -> 轮缘内壁：黄铜包边 + 竖向加强筋
BEAM = [
    "XXXXXXXXXXXXXXXX",
    "dhddhddhddhddhdd",
    "#lm#lm#lm#lm#lmd",
    "#lmXlmXlmXlmXlmd",
    "#lm#lm#lm#lm#lmd",
    "#om#om#om#om#omd",
    "#om#om#om#om#omd",
    "#mm#mm#mm#mm#mmd",
    "#mm#mm#mm#mm#mmd",
    "#om#om#om#om#omd",
    "#om#om#om#om#omd",
    "#lm#lm#lm#lm#lmd",
    "#lmXlmXlmXlmXlmd",
    "#mm#mm#mm#mm#mmd",
    "################",
    "XXXXXXXXXXXXXXXX",
]

# log_top -> 轮毂端盖：与安山款同构，换黄铜色阶
HUB = [
    "XXXXXXXXXXXXXXXX",
    "X##############X",
    "X#whhhhhhhhhhn#X",
    "X#hwonnnnnnowd#X",
    "X#hoddlllllddn#X",
    "X#hnllhhhhlldn#X",
    "X#hnlh####dlnn#X",
    "X#hnlh#XX#dlnn#X",
    "X#hnlh#XX#dlnn#X",
    "X#hnlh####dlnn#X",
    "X#hnlldddddlnn#X",
    "X#hnnlllllllnn#X",
    "X#hwonnnnnnowd#X",
    "X#nddddddddddn#X",
    "X##############X",
    "XXXXXXXXXXXXXXXX",
]

# 金属件：抛光黄铜支架，(1,7) 亮点同时作为轮缘接缝的黄铜螺栓
METAL = [
    "lm..............",
    "md..............",
    "llm.............",
    "lmd.............",
    "md#.............",
    "hhw.............",
    "hnm.............",
    "nw#.............",
    "dd#.............",
    "hnm.............",
    "nnd.............",
    "md#.............",
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
    for name, grid, pal in (('panel', PANEL, WOOD), ('beam', BEAM, BRASS),
                            ('hub', HUB, BRASS), ('metal', METAL, BRASS)):
        paint(grid, pal).save(os.path.join(out, f'brass_water_wheel_{name}.png'))


if __name__ == '__main__':
    main()
