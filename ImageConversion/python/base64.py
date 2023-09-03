#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Aug 12 17:07:35 2023

@author: Ryo Morikage

@OS: macOS M1チップ
"""

from PIL import Image
import base64
import os

def image_to_base64(image_path):
    try:
        with open(image_path, "rb") as image_file:
            image_data = image_file.read()
            base64_data = base64.b64encode(image_data).decode("utf-8")
            return base64_data
    except Exception as e:
        print("An error occurred:", e)
        return None

def base64_to_image(base64_data, output_path):
    try:
        decoded_data = base64.b64decode(base64_data)
        with open(output_path, "wb") as output_file:
            output_file.write(decoded_data)
        print("Decoded data saved to:", output_path)
    except Exception as e:
        print("An error occurred:", e)

if __name__ == "__main__":
    input_path = os.path.abspath('.') + "/input/"
    output_path = os.path.abspath('.') + "/output/"
    
    image_path = input_path + "pochama.jpeg"  # 変換したい画像ファイルのパスを指定してください
    base64_decoded_path = output_path + "base64_decoded_image.jpeg"  # デコード後の画像を保存するパスを指定してください
    
    base64_data = image_to_base64(image_path)
    if base64_data:
        print("Base64 encoded image:")
        print(base64_data)
        base64_to_image(base64_data, base64_decoded_path)
