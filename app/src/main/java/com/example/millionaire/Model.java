package com.example.millionaire;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Model implements Serializable {
    private String name;
    private ArrayList<String> levels;
    private ArrayList<QuestionAndAnswers> questionAndAnswersArrayList;
    private boolean isUsedPrompt50;
    private String moneyBalance;
    private int counter;
    private int numberLevels;

    public Model() {
        this.levels = new ArrayList<>();
        this.questionAndAnswersArrayList = new ArrayList<>();
        counter = 1;
        numberLevels = 10;
        moneyBalance = "0$";
        fillArrays();
    }

    private void fillArrays() {
        levels.add("Level 1: 100$");
        levels.add("Level 2: 200$");
        levels.add("Level 3: 300$");
        levels.add("Level 4: 500$");
        levels.add("Level 5: 1000$");
        levels.add("Level 6: 10000$");
        levels.add("Level 7: 100000$");
        levels.add("Level 8: 250000$");
        levels.add("Level 9: 500000$");
        levels.add("Level 10: 1000000$");
        Collections.reverse(levels);


        ArrayList<String> answers1 = new ArrayList<>();
        answers1.add("Из песни");
        answers1.add("Из конституции");
        answers1.add("Из молитвы");
        answers1.add("Из газет");
        ArrayList<String> answers2 = new ArrayList<>();
        answers2.add("Сломя голову");
        answers2.add("Оборвав уши");
        answers2.add("Выпучив глаза");
        answers2.add("Отбрасывая коньки");
        ArrayList<String> answers3 = new ArrayList<>();
        answers3.add("Красивых любите");
        answers3.add("Женатых губите");
        answers3.add("Наивных дурите");
        answers3.add("Богатых крутите");
        ArrayList<String> answers4 = new ArrayList<>();
        answers4.add("Убежало");
        answers4.add("Отъехало");
        answers4.add("Улетело");
        answers4.add("Утекло");
        ArrayList<String> answers5 = new ArrayList<>();
        answers5.add("Синие ночи");
        answers5.add("Яркие очи");
        answers5.add("Хмурые ночи");
        answers5.add("Ясные очи");
        ArrayList<String> answers6 = new ArrayList<>();
        answers6.add("Барон");
        answers6.add("Маркиз");
        answers6.add("Идальго");
        answers6.add("Вождь");
        ArrayList<String> answers7 = new ArrayList<>();
        answers7.add("Апперкот");
        answers7.add("Кросс");
        answers7.add("Хук");
        answers7.add("Джэб");
        ArrayList<String> answers8 = new ArrayList<>();
        answers8.add("К внучке");
        answers8.add("К Жучке");
        answers8.add("К дочке");
        answers8.add("К залу");
        ArrayList<String> answers9 = new ArrayList<>();
        answers9.add("Автопортрет");
        answers9.add("Самописка");
        answers9.add("Зеркальник");
        answers9.add("Самопал");
        ArrayList<String> answers10 = new ArrayList<>();
        answers10.add("Сосновое яблоко");
        answers10.add("Сладкая репа");
        answers10.add("Желтое яблоко");
        answers10.add("Розовое яблоко");

        QuestionAndAnswers questionAndAnswers1 = new QuestionAndAnswers("Из чего, согласно поговорки, не выкинешь слов?", "Из песни", answers1);
        QuestionAndAnswers questionAndAnswers2 = new QuestionAndAnswers("Как согласно фразеологизму, бежит человек, который сильно спешит?", "Сломя голову", answers2);
        QuestionAndAnswers questionAndAnswers3 = new QuestionAndAnswers("Зачем вы девушки... ?", "Красивых любите", answers3);
        QuestionAndAnswers questionAndAnswers4 = new QuestionAndAnswers("Как говорят о кипящем молоке, перелившемся через край?", "Убежало", answers4);
        QuestionAndAnswers questionAndAnswers5 = new QuestionAndAnswers("Что пионерский гимн призывал взвится кострами?", "Синие ночи", answers5);
        QuestionAndAnswers questionAndAnswers6 = new QuestionAndAnswers("Какой титул имел Дон Кихот?", "Идальго", answers6);
        QuestionAndAnswers questionAndAnswers7 = new QuestionAndAnswers("Что проводит боксер, наносящий удар противнику снизу?", "Апперкот", answers7);
        QuestionAndAnswers questionAndAnswers8 = new QuestionAndAnswers("К кому первому обратились за помощью дед и бабка, не справившись с репкой?", "К внучке", answers8);
        QuestionAndAnswers questionAndAnswers9 = new QuestionAndAnswers("Как называется портрет написаный с самого себя?", "Автопортрет", answers9);
        QuestionAndAnswers questionAndAnswers10 = new QuestionAndAnswers("Как англичане называют \"ананас\"?", "Сосновое яблоко", answers10);

        questionAndAnswersArrayList.add(questionAndAnswers1);
        questionAndAnswersArrayList.add(questionAndAnswers2);
        questionAndAnswersArrayList.add(questionAndAnswers3);
        questionAndAnswersArrayList.add(questionAndAnswers4);
        questionAndAnswersArrayList.add(questionAndAnswers5);
        questionAndAnswersArrayList.add(questionAndAnswers6);
        questionAndAnswersArrayList.add(questionAndAnswers7);
        questionAndAnswersArrayList.add(questionAndAnswers8);
        questionAndAnswersArrayList.add(questionAndAnswers9);
        questionAndAnswersArrayList.add(questionAndAnswers10);
    }

    public void shuffle(){
        Collections.shuffle(questionAndAnswersArrayList);
    }


    public void incrementCounter(){
        counter++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<String> levels) {
        this.levels = levels;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public boolean isUsedPrompt50() {
        return isUsedPrompt50;
    }

    public void setUsedPrompt50(boolean usedPrompt50) {
        isUsedPrompt50 = usedPrompt50;
    }

    public String getMoneyBalance() {
        return moneyBalance;
    }

    public void setMoneyBalance(String moneyBalance) {
        this.moneyBalance = moneyBalance;
    }

    public ArrayList<QuestionAndAnswers> getQuestionAndAnswersArrayList() {
        return questionAndAnswersArrayList;
    }

    public void setQuestionAndAnswersArrayList(ArrayList<QuestionAndAnswers> questionAndAnswersArrayList) {
        this.questionAndAnswersArrayList = questionAndAnswersArrayList;
    }

    public int getNumberLevels() {
        return numberLevels;
    }

    public void setNumberLevels(int numberLevels) {
        this.numberLevels = numberLevels;
    }
}
