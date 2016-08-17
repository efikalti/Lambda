#!/usr/bin/env python
from __future__ import absolute_import
from __future__ import division
from __future__ import print_function
from __future__ import unicode_literals

import json
import os
import subprocess
import time


def execute():

    with open('config.json') as data_file:
        data = json.load(data_file)
    data_file.close()

    if not os.path.isdir(data["directory"]):
        os.makedirs(data["directory"])

    num = int(data["number"])
    iterations = int(data["iterations"])
    wait_time = int(data["wait"])

    for i in range(iterations):
        filename = data['filename'] + str(num)
        bashCommand = "./log_generator.py --logFile " + filename + " --iterations 100"
        process = subprocess.Popen(bashCommand.split(), stdout=subprocess.PIPE)
        output = process.communicate()[0]

        num += 1
        data["number"] = num
        with open('config.json', "w") as data_file:
            json.dump(data, data_file, encoding='utf-8')
        time.sleep(wait_time)
        print("Printed logfile " + filename)


if __name__ == "__main__":
    execute()
