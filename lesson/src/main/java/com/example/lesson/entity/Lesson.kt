package com.example.lesson.entity

class Lesson {
    enum class State {
        PLAYBACK {
            override fun stateName(): String {
                return "有回放"
            }
        },

        LIVE {
            override fun stateName(): String {
                return "正在直播";
            }
        },

        WAIT {
            override fun stateName(): String {
                return "等待中";
            }
        };

        abstract fun stateName(): String
    }

    var date: String
    var content: String
    var state: State

    constructor(date: String, content: String, state: State) {
        this.date = date;
        this.content = content;
        this.state = state;
    }
}