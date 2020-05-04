package cn.tao.entity;

public class PPT {
    private String type;//类型
    private float top;
    private float left;
    private float width;
    private float height;
    private String url;
    private int index;//第几页
    private int theHierarchy; //层级
    private String text;//文本的值
    private float textSize;//文本大小
    private String textColor;//文本样式
    private boolean toBackground;//是否是背景图 true是

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public boolean isToBackground() {
        return toBackground;
    }

    public void setToBackground(boolean toBackground) {
        this.toBackground = toBackground;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTheHierarchy() {
        return theHierarchy;
    }

    public void setTheHierarchy(int theHierarchy) {
        this.theHierarchy = theHierarchy;
    }
}
