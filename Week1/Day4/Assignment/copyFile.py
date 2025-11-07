"""
1) Write script to copy 1 file contents to another file
2) Write script to copy an image file's contents to another file
3) Write script(s) to receive information from console input to create a dictionary
    then append to a file
    Then, be able to read from the file and search for relevant data to return
    Start with name search then can go to any data search.
"""
import os
import random
import json

#1
def copy_file(src, dst = os.path.join(str(random.randint(0,100))+".txt")):
    file1 = open(src, "r")

    with open(dst, "w") as f:
        f.write(file1.read())
        f.write(f"\nCopied from {src}")

    file1.close()
    return dst


#2
def copy_image(src, dst=os.path.join(str(random.randint(0,100))+".dat")):
    file1 = open(src, "rb")
    with open(dst, "wb") as f:
        f.write(file1.read())

    file1.close()
    return dst


#3
def add_to_file(src, dictionary):
    with open(src, "w") as f:
        json.dump(dictionary, f, indent=4)
        f.write(f"\n")

def fill_dictionary(name, age, city):
    return {
        "name" : name,
        "age" : age,
        "city" : city
    }

def search_file(search_topics, file_path):
    with open(file_path, "r") as f:
        data_file = json.load(f)

    return [data_file[x] for x in data_file if x in search_topics]

file = "text.txt"
add_to_file(file, fill_dictionary("alice", 20, "houston"))
topics = ["name"]
print(search_file(topics, file))

copy_file(file, "copiedFile.txt")
copy_image("image.jpg", "copiedImage.jpg")
