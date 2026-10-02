# 安山大水车贴图：逐像素手绘字符网格（不使用程序噪声）
# 用法: python paint_textures.py [输出目录]  默认写入 src/main/resources/.../textures/block
import os
import sys
from PIL import Image

PAL = {
    '#': (58, 66, 62),
    'd': (86, 96, 91),
    'm': (110, 120, 114),
    'n': (132, 141, 135),
    'o': (151, 160, 153),
    'l': (171, 179, 171),
    'h': (191, 198, 189),
}

# planks -> 石板：四条横向石条，底边暗缝，竖缝错开（读起来像原版木板的结构）
PANEL = [
    "lhllllollldhllll",
    "onnonnnmondonnon",
    "nnmnnonnnmdnmnnm",
    "d#ddd#dd#d#dd#dd",
    "llldhlllollllhll",
    "nondonnnnmonnnon",
    "mnndnnmnonnnmdnn",
    "dd#d#dd#ddd#d#dd",
    "hlllllllolllldhl",
    "onnnonmnnnonndon",
    "nmnnnnnmdnnmndnn",
    "#dd#ddd#dd#ddd#d",
    "lllollldhllllllo",
    "nnonnmndonnnonnn",
    "mnnnmnndnnmnnnmn",
    "dd#ddd#d#dd#ddd#",
]

# log -> 梁：比石板暗一档，三道竖向凿槽，少量凹坑与崩口
BEAM = [
    "nmdmnomdmnnmdmno",
    "nmdmnomdmnnm#mno",
    "nm#mnomdmnnmdmnn",
    "omdmnnmdmnomdmnn",
    "nmdmlnmdmnmmdmno",
    "nmdmnnm#mnnmdmnn",
    "nmdmnomdmnnmdmon",
    "omdmnnmdmnlmdmnn",
    "nmdmnomdmnnmdmnn",
    "nmdmnomdmnnm#mno",
    "nm#mnomdmnnmdmnn",
    "nmdmnomdmdddmmnn",
    "omdmnomdmooomdmn",
    "nmdmnnmdmnnmdmno",
    "nmdmnolm#nnmdmnn",
    "nmdmnomdmnomdmnn",
]

# log_top -> 轮毂端面：外框倒角 + 凿刻环槽 + 中心轴孔
HUB = [
    "lllllllllllllllm",
    "lnnnnnnonnnnnnnd",
    "lnnnddddddddnnnd",
    "lnndooooooomdnnd",
    "lndonnnnnnnnmdnd",
    "lndonnnnnnnnmdnd",
    "lndonnmmmmnnmdnd",
    "lndonnm##onnmdnd",
    "lndonnm##onnmdnd",
    "lndonnmooonnmdnd",
    "lndonnnnnnnnmdnd",
    "lndonnnnnnnnmdnd",
    "lnndmmmmmmmmdnnd",
    "lnnnddddddddnnnd",
    "lnnnnnnnnnnonnnd",
    "mddddddddddddddd",
]


def paint(grid):
    assert len(grid) == 16 and all(len(r) == 16 for r in grid), grid
    im = Image.new('RGBA', (16, 16))
    for y, row in enumerate(grid):
        for x, c in enumerate(row):
            im.putpixel((x, y), PAL[c] + (255,))
    return im


def main():
    here = os.path.dirname(os.path.abspath(__file__))
    out = sys.argv[1] if len(sys.argv) > 1 else os.path.join(
        here, '..', '..', 'src', 'main', 'resources', 'assets', 'riverpower', 'textures', 'block')
    for name, grid in (('panel', PANEL), ('beam', BEAM), ('hub', HUB)):
        paint(grid).save(os.path.join(out, f'andesite_water_wheel_{name}.png'))


if __name__ == '__main__':
    main()
