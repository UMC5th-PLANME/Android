import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import com.example.plan_me.databinding.FragmentTimerFocusSettingBinding

class TimerFocusFragment : Fragment(){
    private lateinit var binding: FragmentTimerFocusSettingBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTimerFocusSettingBinding.inflate(layoutInflater)
        initSetting()
        return binding.root
    }

    private fun initSetting() {
        // 바인딩을 사용하여 NumberPicker에 대한 참조 가져오기
        val hour: NumberPicker = binding.timerFocusNumberPickerHour
        val min: NumberPicker = binding.timerFocusNumberPickerMin

        // 순환 막기
        hour.wrapSelectorWheel = false
        min.wrapSelectorWheel = false

        // editText 설정 해제
        hour.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        min.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        // 최소값 설정
        hour.minValue = 0
        min.minValue = 0

        // 최대값 설정
        hour.maxValue = 2

        min.maxValue = 5

        // 보여질 값 설정 (string)
        hour.displayedValues = arrayOf("0", "1", "2")

        // min의 값은 10 단위로 설정
        min.displayedValues = arrayOf("0", "10", "20", "30", "40", "50")
    }
}