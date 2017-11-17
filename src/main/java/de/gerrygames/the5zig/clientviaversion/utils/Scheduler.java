package de.gerrygames.the5zig.clientviaversion.utils;

import eu.the5zig.mod.event.EventHandler;
import eu.the5zig.mod.event.TickEvent;

import java.util.ArrayList;

public class Scheduler {
	private static int taskId;
	private static ArrayList<Task> tasks = new ArrayList<>();

	@EventHandler
	public void onTick(TickEvent e) {
		new ArrayList<>(tasks).forEach(Task::tick);
	}

	public static void clearTasks() {
		tasks.clear();
	}

	public static int runTask(Runnable runnable) {
		return runTaskLater(runnable, 0);
	}

	public static int runTaskLater(Runnable runnable, long delay) {
		return runTaskTimerLater(runnable, -1, delay);
	}

	public static int runTaskTimer(Runnable runnable, long period) {
		return runTaskTimerLater(runnable, period, 0);
	}

	public static int runTaskTimerLater(Runnable runnable, long period, long delay) {
		Task task = new Task(runnable, delay, period);
		tasks.add(task);
		return task.taskId;
	}

	public static int getTaskId() {
		return taskId++;
	}

	public static void cancel(int taskId) {
		tasks.removeIf(t -> t.taskId==taskId);
	}

	public static class Task {
		public Runnable runnable;
		public int taskId;
		public long delay;
		public long tickCount = 0;
		public long period;
		public long periodTickCount;

		public Task(Runnable runnable) {
			this(runnable, 0, -1);
		}

		public Task(Runnable runnable, long delay) {
			this(runnable, delay, -1);
		}

		public Task(Runnable runnable, long delay, long period) {
			this.runnable = runnable;
			this.delay = delay < 0 ? 0 : delay;
			this.period = period < 1 ? -1 : period;
			this.periodTickCount = this.period;
			this.taskId = Scheduler.getTaskId();
		}

		public void tick() {
			if (tickCount++ < delay) return;
			if (period==-1) {
				this.runnable.run();
				this.cancel();
			} else if (++periodTickCount >= period) {
				periodTickCount = 0;
				this.runnable.run();
			}
		}

		public int getTaskId() {
			return taskId;
		}

		public void cancel() {
			Scheduler.cancel(taskId);
		}
	}
}
