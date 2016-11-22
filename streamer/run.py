#!/usr/bin/env python

from bitcoin_streamer import BC_Streamer
import json
from websocket import create_connection

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
    ws = create_connection("wss://ws.blockchain.info/inv")
    print "Sending 'op ping'..."
    ws.send('{"op": "unconfirmed_sub"}')
    #ws.send('{"op": "blocks_sub"}')
    n = 0
    while 1:
        print "Receiving..."
        result = ws.recv()
        print "Received '%s'" % result
        n += 1
        print n
    print "Sent"
    print "Receiving..."
    result = ws.recv()
    print "Received '%s'" % result
    ws.close()
    #main()
