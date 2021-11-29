package br.com.lestat.everday_thinking.model

class UltimasNovidades {
    var news: List<News>? = null

    class News {
        var user: User? = null
        var message: Message? = null
    }

    class User {
        var name: String? = null
        var profile_picture: String? = null
    }

    class Message {
        var content: String? = null
        var created_at: String? = null
    }
}