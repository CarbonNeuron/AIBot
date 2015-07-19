/*******************************************************************************
 *     Copyright (C) 2015 Jordan Dalton (jordan.8474@gmail.com)
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along
 *     with this program; if not, write to the Free Software Foundation, Inc.,
 *     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *******************************************************************************/
package ovh.tgrhavoc.aibot.ai;

public interface Task {
	/**
	 * Returns true if the precondition to activate it is met.
	 */
	public boolean isPreconditionMet();

	/**
	 * Starts the task.
	 */
	public boolean start(String... options);

	/**
	 * Stops the task. This is called either when it becomes inactive or it is
	 * told to stop.
	 */
	public void stop();

	/**
	 * Called every game tick that it is active.
	 */
	public void run();

	/**
	 * Returns true as long as the task can continue to run.
	 */
	public boolean isActive();

	/**
	 * Returns the priority of the task. This only pertains to tasks that are
	 * exclusive. If multiple exclusive tasks are active, the task with the
	 * highest priority will take precedence. If the there is more than one task
	 * of highest priority, the task that was started first will take
	 * precedence.
	 * 
	 * @see Task#isExclusive()
	 */
	public TaskPriority getPriority();

	/**
	 * Returns true if all other tasks should be put on hold while this task is
	 * active.
	 * 
	 * @see Task#getPriority()
	 */
	public boolean isExclusive();

	/**
	 * Returns true if this task ignores other active tasks that are exclusive.
	 */
	public boolean ignoresExclusive();

	/**
	 * The name of the task (e.g. FollowTask would have the name Follow)
	 */
	public String getName();

	/**
	 * Describes the options provided to start the task.
	 */
	public String getOptionDescription();
}
