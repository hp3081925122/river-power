# 安山大水车贴图 v2：逐像素手绘字符网格（不使用程序噪声）
# 风格对齐 Create 安山系：安山合金色阶 + 深色铁件
# 用法: python paint_textures.py [输出目录]  默认写入 src/main/resources/.../textures/block
#
# UV 速查（Create 大水车 OBJ）：
#   panel -> 轮缘正侧面、十字横梁、叶片；板条沿圆周走向，第 0 行是外缘
#   beam  -> 轮缘内壁，只采样 x0-9、y0.5-14.5
#   hub   -> 中心端盖，采样 1-15
#   metal -> 叶片根部支架与轮缘接缝螺栓，只采样 x0-2、y0-11；(2,5)、(1,7) 单像素会被当作螺栓点
import os
import sys
from PIL import Image

# Create 安山合金色阶（略带青绿）
ALLOY = {
    'W': (226, 232, 219),
    'h': (203, 210, 197),
    'l': (182, 192, 182),
    'm': (163, 174, 166),
    'n': (142, 154, 147),
    'o': (123, 135, 128),
    'd': (103, 115, 108),
    'x': (80, 90, 87),
    'k': (60, 68, 66),
}

# 支架用深色铁
IRON = {
    'I': (170, 175, 174),
    'i': (128, 134, 135),
    'j': (97, 102, 105),
    'q': (72, 76, 80),
    'z': (52, 55, 60),
    'Z': (38, 40, 45),
}

# planks -> 安山合金压条：四条通长压条（顶边受光、底边暗缝），铆钉每 8 像素错位排列
PANEL = [
    "llllllllllllllll",
    "mmmWmllllmmWmmmm",
    "nnndnnnnnnndnnnn",
    "dddddddddddddddd",
    "llllllllllllllll",
    "lllmmmmWmmmmllmW",
    "nnnnnnndnnnnnnnd",
    "dddddddddddddddd",
    "llllllllllllllll",
    "mmmWmmmmmmmWmlll",
    "nnndnnonnnndnnnn",
    "dddddddddddddddd",
    "llllllllllllllll",
    "mmlllmmWmmmmmmmW",
    "nnnnnnndnnonnnnd",
    "dddddddddddddddd",
]

# log -> 轮缘内壁：上下压边 + 竖向加强筋，整体比压条暗一档，让内圈退到阴影里
BEAM = [
    "xxxxxxxxxxxxxxxx",
    "nhnnhnnhnnhnnhnn",
    "olnolnolnolnolnn",
    "olndlndlndlndlnd",
    "olnolnolnolnolnd",
    "olnolnolnolnolnd",
    "olnolnolnolnolnd",
    "olnolnolnolnolnd",
    "olnolnolnolnolnd",
    "olnolnolnolnolnd",
    "olnolnolnolnolnd",
    "olnolnolnolnolnd",
    "olndlndlndlndlnd",
    "onnonnonnonnonnd",
    "dddddddddddddddd",
    "xxxxxxxxxxxxxxxx",
]

# log_top -> 轮毂端盖：深色外框、倒角、四角铆钉、中心轴座
HUB = [
    "kkkkkkkkkkkkkkkk",
    "kxxxxxxxxxxxxxxk",
    "kxWhhhhhhhhhhnxk",
    "kxhWonnnnnnoWdxk",
    "kxhoddlllllddnxk",
    "kxhnllhhhhlldnxk",
    "kxhnlhxxxxdlnnxk",
    "kxhnlhxkkxdlnnxk",
    "kxhnlhxkkxdlnnxk",
    "kxhnlhxxxxdlnnxk",
    "kxhnlldddddlnnxk",
    "kxhnnlllllllnnxk",
    "kxhWonnnnnnoWdxk",
    "kxnddddddddddnxk",
    "kxxxxxxxxxxxxxxk",
    "kkkkkkkkkkkkkkkk",
]

# 金属件：深色铁支架，'.' 为透明（OBJ 不采样的区域）
METAL = [
    "jq..............",
    "qz..............",
    "jjq.............",
    "jqz.............",
    "qzZ.............",
    "iiI.............",
    "ijq.............",
    "jzq.............",
    "qqZ.............",
    "ijq.............",
    "jjq.............",
    "qzZ.............",
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
    for name, grid, pal in (('panel', PANEL, ALLOY), ('beam', BEAM, ALLOY),
                            ('hub', HUB, ALLOY), ('metal', METAL, IRON)):
        paint(grid, pal).save(os.path.join(out, f'andesite_water_wheel_{name}.png'))


if __name__ == '__main__':
    main()
