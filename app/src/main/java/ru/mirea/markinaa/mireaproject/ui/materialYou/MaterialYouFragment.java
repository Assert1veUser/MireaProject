package ru.mirea.markinaa.mireaproject.ui.materialYou;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import ru.mirea.markinaa.mireaproject.R;

public class MaterialYouFragment extends Fragment {

    private TextView textView_heading;
    private TextView textView_main;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_material_you, container, false);
        textView_heading = (TextView) view.findViewById(R.id.textView2);
        textView_main = (TextView) view.findViewById(R.id.textView3);
        textView_heading.setText("Интересные факты о создании компьютерных игр");
        textView_main.setText("Создание компьютерных игр - это захватывающий процесс, который " +
                "требует множества труда и усилий. В этом блог-посте я расскажу несколько " +
                "интересных фактов о создании компьютерных игр, которые, возможно, вы еще не знали."
                + "\n" + "\n" +
                "1. Название игры может измениться в процессе разработки\n" +
                "Иногда название игры не подходит к ее концепту. В таком случае разработчики " +
                "могут изменить название в ходе разработки. Например, изначальное название " +
                "Grand Theft Auto было \"Race’n’Chase\".\n" +
                "2. Некоторые игры были созданы по ошибке\n" +
                "Некоторые знаменитые игры были созданы случайно. Например, Pac-Man был " +
                "создан как концепт для игры в формате ресторана, но его игровая механика " +
                "была настолько увлекательной, что его сделали в отдельную игру.\n" +
                "3. Музыка игр может вдохновляться реальными инструментами\n" +
                "Многие знаменитые игры включают музыку, которая была написана на реальных " +
                "музыкальных инструментах. Например, музыка из игры The Elder Scrolls V: Skyrim " +
                "была написана на органе, а музыка из игры Halo была написана на гитаре.\n" +
                "4. Некоторые игры могут быть созданы за несколько месяцев\n" +
                "Несмотря на то, что большинство игр занимают годы разработки, некоторые " +
                "игры могут быть созданы за несколько месяцев. Например, игра Flappy Bird " +
                "была создана за два дня.\n" +
                "5. Игры могут быть созданы на различных платформах\n" +
                "Существует множество платформ для создания компьютерных игр, включая PC, " +
                "консоли и мобильные устройства. Некоторые игры могут быть созданы на " +
                "нескольких платформах одновременно.\n" +
                "\n" +
                "Эти факты о создании компьютерных игр подчеркивают трудности и " +
                "увлекательность процесса и помогут вам лучше понять, что происходит " +
                "за кулисами. Игры - это не просто развлечение, это целый мир, который " +
                "создается из того, что есть вокруг нас.");
        return view;
    }
}