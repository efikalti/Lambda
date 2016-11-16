#!/usr/bin/env python

from bitcoin_streamer import BC_Streamer
import json

def main():
    bstreamer = BC_Streamer()

    try:
        blocks = bstreamer.get_blocks_of_the_day()
        block = blocks[0]
        b = bstreamer.get_block(block.hash)
        print bstreamer.block_to_json(b)
    except Exception as e:
        print e


if __name__ == "__main__":
   main()
