#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Aug 12 17:36:30 2023

@author: Ryo Morikage

@OS: macOS M1チップ
"""

import zlib
import os

def compress_image(image_path, output_path):
    try:
        with open(image_path, "rb") as image_file:
            image_data = image_file.read()
            compressed_data = zlib.compress(image_data)
            with open(output_path, "wb") as output_file:
                output_file.write(compressed_data)
            print("Compressed data saved to:", output_path)
    except Exception as e:
        print("An error occurred:", e)

def decompress_image(compressed_path, output_path):
    try:
        with open(compressed_path, "rb") as compressed_file:
            compressed_data = compressed_file.read()
            decompressed_data = zlib.decompress(compressed_data)
            with open(output_path, "wb") as output_file:
                output_file.write(decompressed_data)
            print("Decompressed data saved to:", output_path)
    except Exception as e:
        print("An error occurred:", e)

if __name__ == "__main__":
    input_path = os.path.abspath('.') + "/input/"
    output_path = os.path.abspath('.') + "/output/"
    
    image_path = input_path + "pochama.jpeg"  # 変換したい画像ファイル名前を指定してください
    compressed_path = output_path + "compressed_image.zlib"  # 圧縮後のファイルを保存する絶対パスを指定してください
    decompressed_path = output_path + "decompressed_image.jpeg"  # 解凍後のファイルを保存する絶対パスを指定してください

    compress_image(image_path, compressed_path)
    decompress_image(compressed_path, decompressed_path)
