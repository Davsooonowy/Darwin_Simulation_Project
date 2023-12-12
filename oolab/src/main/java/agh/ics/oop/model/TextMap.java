//package agh.ics.oop.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TextMap implements WorldMap<String, Integer> {
//    private List<String> textList = new ArrayList<>();
//
//    @Override
//    public boolean place(String text) {
//        textList.add(text);
//        return true;
//    }
//
//    @Override
//    public void move(String text, MoveDirection direction) {
//        int index = textList.indexOf(text);
//        if (index != -1 && (direction == MoveDirection.FORWARD || direction == MoveDirection.BACKWARD)) {
//            int newIndex = direction == MoveDirection.FORWARD ? index + 1 : index - 1;
//            if (newIndex >= 0 && newIndex < textList.size()) {
//                String neighbor = textList.get(newIndex);
//                textList.set(newIndex, text);
//                textList.set(index, neighbor);
//            }
//        }
//    }
//
//    @Override
//    public boolean isOccupied(Integer position) {
//        return position >= 0 && position < textList.size();
//    }
//
//    @Override
//    public String objectAt(Integer position) {
//        if (isOccupied(position)) {
//            return textList.get(position);
//        }
//        return null;
//    }
//
//    @Override
//    public boolean canMoveTo(Vector2d position) {
//        return false;
//    }
//
//    public String toString() {
//        StringBuilder result = new StringBuilder();
//        for (String line : textList) {
//            result.append(line).append(" ");
//        }
//        return result.toString();
//    }
//}