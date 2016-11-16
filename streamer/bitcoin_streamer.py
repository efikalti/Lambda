#!/usr/bin/env python

from blockchain import blockexplorer
import functions
import json

class BC_Streamer:

    def __init__(self):
        pass


    def get_latest_block(self):
        return blockexplorer.get_latest_block()


    def get_block(self, hash=None):
        if hash is None:
            return None
        return blockexplorer.get_block(hash)


    def get_blocks_of_the_day(self):
        time = functions.current_time_to_ms()
        return blockexplorer.get_blocks(time=time)


    def block_to_json(self, block=None):
        if block is None:
            return None

        block_json = dict()
        block_json["hash"] = block.hash
        block_json["version"] = block.version
        block_json["previous_block"] = block.previous_block
        block_json["merkle_root"] = block.merkle_root
        block_json["time"] = block.time
        block_json["bits"] = block.bits
        block_json["fee"] = block.fee
        block_json["nonce"] = block.nonce
        block_json["n_tx"] = block.n_tx
        block_json["size"] = block.size
        block_json["block_index"] = block.block_index
        block_json["main_chain"] = block.main_chain
        block_json["height"] = block.height
        block_json["received_time"] = block.received_time
        block_json["relayed_by"] = block.relayed_by
        block_json["transactions"] = block.transactions
        return block_json
