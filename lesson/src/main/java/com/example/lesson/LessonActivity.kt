package com.example.lesson

import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.core.BaseView
import com.example.core.utils.CacheUtils
import com.example.lesson.entity.Lesson
import okhttp3.Cache
import kotlin.reflect.KProperty

class LessonActivity : AppCompatActivity(), BaseView<LessonPresenter>,
    Toolbar.OnMenuItemClickListener {

    override val presenter: LessonPresenter by lazy {
        LessonPresenter(this)
    }

    private val lessonAdapter = LessonAdapter()

    private lateinit var refreshLayout: SwipeRefreshLayout

    class Saver(private val s: String){
        operator fun getValue(lessonActivity: LessonActivity, property: KProperty<*>): String {
            return CacheUtils.get(s)!!
        }

        operator fun setValue(lessonActivity: LessonActivity, property: KProperty<*>, value: String) {
            CacheUtils.save(s, value)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lesson)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_lesson)

        findViewById<RecyclerView>(R.id.list).run {
            layoutManager = LinearLayoutManager(this@LessonActivity)
            adapter = lessonAdapter
            addItemDecoration(DividerItemDecoration(this@LessonActivity, LinearLayout.VERTICAL))
        }

        refreshLayout = findViewById<SwipeRefreshLayout?>(R.id.swipe_refresh_layout).also {
            it.setOnRefreshListener {
                presenter.fetchData()
            }
            it.isRefreshing = true
        }

        presenter.fetchData()
    }

    internal fun showResult(lessons: List<Lesson>) {
        lessonAdapter.updateAndNotify(lessons)
        refreshLayout.isRefreshing = false
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        presenter.showPlayback()
        return false
    }

}