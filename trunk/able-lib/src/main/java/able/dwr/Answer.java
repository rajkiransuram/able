package able.dwr;

import org.directwebremoting.annotations.DataTransferObject;

@DataTransferObject
public class Answer {
    private int answer;

    public Answer() {
    }

    public Answer(int answer) {
        this.answer = answer;
    }

    public int getAnswer() {
        return answer;
    }
}
