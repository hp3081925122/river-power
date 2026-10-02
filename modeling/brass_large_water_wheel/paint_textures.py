# 黄铜大水车贴图：配色取自 Create 的 brass_block / brass_casing，逐像素手绘字符网格
# 结构对应黄铜机壳：黄铜梁/轮毂 + 深色木板叶片
# 用法: python paint_textures.py [输出目录]  默认写入 src/main/resources/.../textures/block
import os
import sys
from PIL import Image

HERE = os.path.dirname(os.path.abspath(__file__))
ANDESITE_DIR = os.path.join(HERE, '..', 'andesite_large_water_wheel')

# Create brass_block 色阶
BRASS = {
    'X': (89, 36, 36),
    '#': (114, 71, 49),
    'd': (158, 105, 71),
    'm': (191, 142, 85),
    'n': (206, 160, 90),
    'o': (215, 170, 94),
    'l': (228, 183, 99),
    'h': (247, 203, 108),
    'w': (255, 235, 140),
}

# Create brass_casing 内部深色木
WOOD = {
    '#': (48, 30, 14),
    'd': (58, 36, 17),
    'm': (68, 43, 21),
    'n': (79, 50, 24),
    'o': (90, 62, 30),
    'l': (100, 68, 33),
    'h': (114, 78, 38),
}

# planks -> 深色木板：四条横板，顶边受光，底边暗缝，竖缝错开
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

# log -> 黄铜梁：两侧暗边，斜向抛光高光（同 brass_block 的斜纹），周期 8 竖向无缝
BEAM = [
    "mlohlnnnnnohlnmd",
    "mlnohlnnnnnohlmd",
    "mlnnohlnnnnnohmd",
    "mlnnnohlnnnnnomd",
    "mlnnnnohlnnnnnmd",
    "mhlnnnnohlnnnnmd",
    "mhlnnnnnohlnnnmd",
    "mlhlnnnnnohlnnmd",
    "mlohlnnnnnohlnmd",
    "mlnohlnnnnnohlmd",
    "mlnnohlnnnnnohmd",
    "mlnnnohlnnnmnomd",
    "mlnnnnohlnnnnnmd",
    "mhlnnmnohlnnnnmd",
    "mhlnnnnnohlnnnmd",
    "mlhlnnnnnohlnnmd",
]

# log_top -> 轮毂端面：亮黄铜外框倒角，内圈压槽，中心深色轴孔
HUB = [
    "wwwwwwwwwwwwwwwh",
    "whhhhhhlhhhhhhh#",
    "whhh########hhh#",
    "whh#llllllld#hh#",
    "wh#lnnnnnnnnd#h#",
    "wh#lnnnnnnnnd#h#",
    "wh#lnnddddnnd#h#",
    "wh#lnndXXmnnd#h#",
    "wh#lnndXXmnnd#h#",
    "wh#lnndmmmnnd#h#",
    "wh#lnnnnnnnnd#h#",
    "wh#lnnnnnnnnd#h#",
    "whh#dddddddd#hh#",
    "whhh########hhh#",
    "whhhhhhhhhhlhhh#",
    "h###############",
]

# metal：沿用安山版金属件的形状，灰阶按亮度一一换成黄铜色阶
# 整体比梁暗一档，避免金属件贴在黄铜梁上糊成一片
METAL_RAMP = [(60, 30, 26)] + [BRASS[k] for k in ('X', '#', 'd', 'm', 'n', 'l')]


def paint(grid, pal):
    assert len(grid) == 16 and all(len(r) == 16 for r in grid), grid
    im = Image.new('RGBA', (16, 16))
    for y, row in enumerate(grid):
        for x, c in enumerate(row):
            im.putpixel((x, y), pal[c] + (255,))
    return im


def recolor_metal():
    src = Image.open(os.path.join(ANDESITE_DIR, 'textures', 'andesite_water_wheel_metal.png')).convert('RGBA')
    grays = sorted({c for c in src.getdata() if c[3]}, key=lambda c: sum(c[:3]))
    assert len(grays) <= len(METAL_RAMP), grays
    # 灰阶少于色阶时，均匀挑选色阶
    step = (len(METAL_RAMP) - 1) / max(1, len(grays) - 1)
    lut = {g: METAL_RAMP[round(i * step)] + (g[3],) for i, g in enumerate(grays)}
    out = Image.new('RGBA', src.size)
    out.putdata([lut.get(c, c) for c in src.getdata()])
    return out


def main():
    out = sys.argv[1] if len(sys.argv) > 1 else os.path.join(
        HERE, '..', '..', 'src', 'main', 'resources', 'assets', 'riverpower', 'textures', 'block')
    os.makedirs(out, exist_ok=True)
    paint(PANEL, WOOD).save(os.path.join(out, 'brass_water_wheel_panel.png'))
    paint(BEAM, BRASS).save(os.path.join(out, 'brass_water_wheel_beam.png'))
    paint(HUB, BRASS).save(os.path.join(out, 'brass_water_wheel_hub.png'))
    recolor_metal().save(os.path.join(out, 'brass_water_wheel_metal.png'))


if __name__ == '__main__':
    main()
