package com.tweener.alarmee

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
