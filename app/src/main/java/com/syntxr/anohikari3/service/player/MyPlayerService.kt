package com.syntxr.anohikari3.service.player

import snow.player.PlayerService
import snow.player.annotation.PersistenceId

@PersistenceId("com.syntxr.anohikari3.service.player.MyPlayerService")
class MyPlayerService : PlayerService()