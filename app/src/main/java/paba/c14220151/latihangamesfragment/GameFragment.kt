package paba.c14220151.latihangamesfragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.content.Context
import android.content.SharedPreferences

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {
    private var score = 50
    private lateinit var numbers: MutableList<Int>
    private var firstSelection: Int? = null
    private var secondSelection: Int? = null
    private lateinit var buttons: List<Button>
    private var maxNumber =5
    private lateinit var sharedPreferences: SharedPreferences



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //inflate game fragment layout
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("GameSettings", Context.MODE_PRIVATE)
        maxNumber = sharedPreferences.getInt("max_number", 5) // Ambil nilai maksimum dari SharedPreferences
        setupGame()
    }

    private fun setupGame() {
        // membuat array untuk menyimpan angka
        numbers = mutableListOf()
        for (i in 1..maxNumber) {
            numbers.add(i)
            numbers.add(i)
        }
        numbers.shuffle()

        // setting button grid
        val gridLayout = view?.findViewById<GridLayout>(R.id.gridLayout)
        buttons = List(numbers.size) { index ->
            Button(requireContext()).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    setMargins(8, 8, 8, 8)
                }
                text = "?"
                setOnClickListener {
                    onButtonClick(index)
                }
                gridLayout?.addView(this)
            }
        }

        // update tampilan skor
        updateScoreDisplay()
    }

    private fun onButtonClick(index: Int) {
        if (buttons[index].text != "?") return

        when {
            firstSelection == null -> {
                firstSelection = index
                buttons[index].text = numbers[index].toString()
            }

            secondSelection == null && index != firstSelection -> {
                secondSelection = index
                buttons[index].text = numbers[index].toString()

                // Check match
                Handler(Looper.getMainLooper()).postDelayed({
                    checkMatch()
                }, 1000)
            }
        }
    }

    private fun checkMatch() {
        val first = firstSelection!!
        val second = secondSelection!!

        if (numbers[first] == numbers[second]) {
            score += 10
            buttons[first].isEnabled = false
            buttons[second].isEnabled = false
        } else {
            score -= 5
            buttons[first].text = "?"
            buttons[second].text = "?"
        }

        firstSelection = null
        secondSelection = null
        updateScoreDisplay()
        (activity as MainActivity).updateScore(score)
    }

    private fun updateScoreDisplay() {
        view?.findViewById<TextView>(R.id.scoreText)?.text = "Score: $score"
    }
}