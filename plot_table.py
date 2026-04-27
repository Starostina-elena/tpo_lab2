#!/usr/bin/env python3
"""
Простой скрипт для построения графика из table.csv
Сохранит изображение в diagram.png

Установка зависимостей:
    pip install matplotlib

Запуск:
    python3 plot_table.py table.csv

Если хотите интерактивно смотреть — можно запустить без аргумента, тогда скрипт попытается найти ./table.csv
"""
import sys
import csv
import math
import matplotlib.pyplot as plt

CSV_PATH = sys.argv[1] if len(sys.argv) > 1 else 'table.csv'

xs = []
ys = []

with open(CSV_PATH, newline='') as f:
    reader = csv.reader(f)
    header = next(reader, None)
    for row in reader:
        if not row:
            continue
        # ожидается: X, value
        try:
            x_str = row[0].strip()
            y_str = row[1].strip() if len(row) > 1 else ''
        except Exception:
            continue

        try:
            x = float(x_str)
        except Exception:
            continue

        # y может быть NaN
        if y_str.lower() == 'nan' or y_str == '':
            y = float('nan')
        else:
            try:
                y = float(y_str)
            except Exception:
                y = float('nan')

        xs.append(x)
        ys.append(y)

# Разделим на валидные и NaN для отображения
x_valid = [x for x, y in zip(xs, ys) if not math.isnan(y)]
y_valid = [y for y in ys if not math.isnan(y)]

# Настройки графика
plt.figure(figsize=(10, 6))
plt.plot(x_valid, y_valid, marker='o', linestyle='-')

# Отметим точки, где NaN (если есть)
nan_points = [(x, y) for x, y in zip(xs, ys) if math.isnan(y)]
if nan_points:
    xn, yn = zip(*nan_points)
    # покажем NaN как пустые кресты на оси x
    plt.scatter(xn, [0]*len(xn), color='red', marker='x', label='NaN (plotted at y=0)')

plt.xlabel('X')
plt.ylabel('Результаты модуля (X)')
plt.title('График значений из table.csv')
plt.grid(True)
plt.tight_layout()
plt.savefig('diagram.png', dpi=150)
print("Saved plot to diagram.png")
# также показать интерактивно, если запуск в среде с GUI
if sys.stdout.isatty():
    try:
        plt.show()
    except Exception:
        pass

