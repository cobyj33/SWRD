package swrd.game.logic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeyTracker {
	private List<Integer> pressedKeys;
	private HashMap<Integer, KeyAction> bindings;
	
	public static final int LEFT_CLICK = -1, RIGHT_CLICK = -2;
	public static final int ONPRESS = 0, WHILEPRESSED = 1, ONRELEASE = 2;
	
	public KeyTracker() {
		pressedKeys = new ArrayList<>();
		bindings = new HashMap<>();
	}
	
	public void pressKey(int keyCode) {
		if (pressedKeys.contains(keyCode)) { return; }
		if (bindings.containsKey(keyCode))
			bindings.get(keyCode).doPress();
		pressedKeys.add(Integer.valueOf(keyCode));
	}
	
	public void releaseKey(int keyCode) {
		if (bindings.containsKey(keyCode))
			bindings.get(keyCode).doRelease();
		pressedKeys.remove(Integer.valueOf(keyCode));
	}
	
	public boolean isPressed(int keyCode) {
		if (pressedKeys.contains(keyCode)) {
			return true;
		}
		return false;
	}
	
	public List<Integer> getPressedKeys() {
		return pressedKeys;
	}
	
	public void addBinding(int keyCode, Runnable onPress, Runnable whilePressed, Runnable onRelease) {
		bindings.put(keyCode, new KeyAction(onPress, whilePressed, onRelease));
	}
	
	public void removeBinding(int keyCode) {
		if (bindings.containsKey(keyCode))
			bindings.remove(keyCode);
	}
	
	public void addAction(Runnable runnable, int key, int choice) {
		KeyAction actionList = new KeyAction();
		if (bindings.containsKey(key)) {
			actionList = bindings.get(key);
		} else {
			actionList = new KeyAction();
			bindings.put(key, actionList);
		}
		
		switch (choice) {
			case ONPRESS: actionList.getPressedActions().add(runnable); break;
			case WHILEPRESSED: actionList.getWhilePressedActions().add(runnable); break;
			case ONRELEASE: actionList.getReleaseActions().add(runnable); break;
			default: System.out.println("Invalid choice");
		}
	}
	
	public void removeAction(int keyCode, int choice) {
		if (bindings.containsKey(keyCode)) { 
			KeyAction binding = bindings.get(keyCode);
			switch (choice) {
			case ONPRESS: binding.getPressedActions().clear(); break;
			case WHILEPRESSED: binding.getWhilePressedActions().clear(); break;
			case ONRELEASE: binding.getReleaseActions().clear(); break;
			default: System.out.println("Invalid choice");
		}
		}
	}
	
	
	
	public void processActions() {
		
		for (int k = 0; k < pressedKeys.size(); k++) {
			int key = pressedKeys.get(k);
			if (bindings.containsKey(key))
				bindings.get(key).doWhilePressed();
		}
	}
	
	
}

class KeyAction {
	private List<Runnable> pressedActions;
	private List<Runnable> whilePressedActions;
	private List<Runnable> releaseActions;
	
	public KeyAction() {
		pressedActions = new ArrayList<>(1);
		whilePressedActions = new ArrayList<>(1);
		releaseActions = new ArrayList<>(1);
	}
	
	public KeyAction(Runnable onPress, Runnable whilePressed, Runnable onRelease) {
		pressedActions = new ArrayList<>(1);
		whilePressedActions = new ArrayList<>(1);
		releaseActions = new ArrayList<>(1);
		if (onPress != null) pressedActions.add(onPress);
		if (whilePressed != null) whilePressedActions.add(whilePressed);
		if (onRelease != null) releaseActions.add(onRelease);
	}
	
	public void doWhilePressed() {
		for (int i = 0; i < whilePressedActions.size(); i++) {
			whilePressedActions.get(i).run();
		}
	}
	
	public void doPress() {
		for (int i = 0; i < pressedActions.size(); i++) {
			pressedActions.get(i).run();
		}
	}
	
	public void doRelease() {
		for (int i = 0; i < releaseActions.size(); i++) {
			releaseActions.get(i).run();
		}
	}

	public List<Runnable> getPressedActions() {
		return pressedActions;
	}

	public List<Runnable> getWhilePressedActions() {
		return whilePressedActions;
	}

	public List<Runnable> getReleaseActions() {
		return releaseActions;
	}
	
	
	
	
	
	
}

