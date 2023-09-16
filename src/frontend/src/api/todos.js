import client from "./client.js";
import { getTodosUrl } from "./urls.js";

export const getAllTodos = () => client.get(getTodosUrl())
export const updateTodo = (todo) => client.put(getTodosUrl(todo.id), todo)
