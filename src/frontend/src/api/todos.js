import client from "./client.js";
import { TODOS_URL } from "./urls.js";

export const getAllTodos = async () => client.get(TODOS_URL)
