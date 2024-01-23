package com.syntxr.anohikari2.service.player

import snow.player.PlayerService
import snow.player.annotation.PersistenceId

@PersistenceId("com.syntxr.anohikari2.service.player.MyPlayerService")
class MyPlayerService : PlayerService()