import client from "./client.js";
import { getTodosUrl } from "./urls.js";

export const getAllTodos = () => client.get(getTodosUrl())
export const createTodo = (todo) => client.post(getTodosUrl(), todo)
export const updateTodo = (todo) => client.put(getTodosUrl(todo.id), todo)
