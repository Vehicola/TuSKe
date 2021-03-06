package me.tuke.sktuke.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import javax.annotation.Nullable;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import me.tuke.sktuke.TuSKe;

public class CondHasGUI extends Condition{

	private Expression<Player> p;
	private Expression<Number> obj = null;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg, int arg1, Kleenean arg2, ParseResult arg3) {
		if (arg.length > 1){
			obj = (Expression<Number>) arg[0];
			p = (Expression<Player>) arg[1];
			
		} else {
			p = (Expression<Player>) arg[0];
		}
		setNegated(arg1 > 1);
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return this.p + "' inventory has a gui";
	}

	@Override
	public boolean check(Event e) {
		boolean r = false;
		if (p.getSingle(e) == null)
			return false;
		if (obj != null){
			if (obj.getSingle(e) == null) 
				return false;
			r = TuSKe.getGUIManager().isGUI(p.getSingle(e).getOpenInventory().getTopInventory(), ((Number)obj.getSingle(e)).intValue());
		} else
			r = TuSKe.getGUIManager().hasGUI(p.getSingle(e).getOpenInventory().getTopInventory());
		if (isNegated())
			r = !r;
		return r;
	}

}
