package com.nkr.androidanimation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nkr.androidanimation.R
import com.nkr.androidanimation.adapter.DemosAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(),CompoundButton.OnCheckedChangeListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var doShowPaths = false

    private val dataset: Array<DemosAdapter.Demo> = arrayOf(
        DemosAdapter.Demo("Property Animation", "Basic motion example using referenced ConstraintLayout files", R.layout.motion_01_basic),
        DemosAdapter.Demo("Polygonal Animation", "Polygonal animation on custom view drawable", R.layout.polygonal_animation),
        DemosAdapter.Demo("Progress Animation", "Polygonal animation on custom view drawable", R.layout.layout_progress_animation),
        DemosAdapter.Demo("Random Path Animation", "Random path animation on custom view drawable", R.layout.activity_path_animation),
        DemosAdapter.Demo("Circle Animation", "Circle animation on custom view drawable", R.layout.layout_circle_animation_drawable),
        DemosAdapter.Demo("Starfield Animation", "Starfield animation simulation", R.layout.layout_starfield),
        DemosAdapter.Demo("Another Path Animation", "Path animation on a straight line", R.layout.layout_path_animation)

        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewManager = LinearLayoutManager(this)
        viewAdapter = DemosAdapter(dataset)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        showPaths.setOnCheckedChangeListener(this)

    }




    override fun onCheckedChanged(p0: CompoundButton?, value: Boolean) {
        doShowPaths = value
    }

    fun start(activity: Class<*>, layoutFileId: Int) {
        val intent = Intent(this, activity).apply {
            putExtra("layout_file_id", layoutFileId)
            putExtra("showPaths", doShowPaths)
        }
        startActivity(intent)
    }
}
