import client from "./client.js";
import { getTodosUrl } from "./urls.js";

export const getAllTodos = () => client.get(getTodosUrl())
export const createTodo = (todo) => client.post(getTodosUrl(), todo)
export const updateTodo = (todo) => client.put(getTodosUrl(todo.id), todo)
export const toggleTodoCompleted = (id, completed) => client.put(getTodosUrl(id, completed ? 'uncheck' : 'check'))
export const deleteTodo = (id) => client.delete(getTodosUrl(id))
