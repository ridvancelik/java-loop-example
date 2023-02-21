package streams;

public class ElemWithAvg {
    private Element element;
    private int average;

    public ElemWithAvg(Element element, int average) {
        this.element = element;
        this.average = average;
    }

    public Element getElement() {
        return element;
    }

    public ElemWithAvg setElement(Element element) {
        this.element = element;
        return this;
    }

    public int getAverage() {
        return average;
    }

    public ElemWithAvg setAverage(int average) {
        this.average = average;
        return this;
    }
}
