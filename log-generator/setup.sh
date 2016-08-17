#!/bin/bash

(crontab -l ; echo "*/20 * * * * $PWD/run_generator.py")| crontab -
