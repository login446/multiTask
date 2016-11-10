package com.alex.multitask;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by alex on 01.11.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@Sql("/scriptTest.sql")
public class WebControllerTaskTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllTasksNoTextNoComments() throws Exception {
        mockMvc.perform(get("/task/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].taskText").value(""))
                .andExpect(jsonPath("$[0].comments.length()").value(0))
                .andExpect(jsonPath("$[1].taskText").value(""))
                .andExpect(jsonPath("$[1].comments.length()").value(0))
                .andExpect(jsonPath("$[2].taskText").value(""))
                .andExpect(jsonPath("$[2].comments.length()").value(0));
    }

    @Test
    public void testGetAllTasksByFilter() throws Exception {
        mockMvc.perform(get("/task/filter")
                .param("authorId", "1")
                .param("executorId", "2")
                .param("status", "new")
                .param("deadline", "2016/11/09 17:23"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].authorId").value("1"))
                .andExpect(jsonPath("$[0].executorId").value("2"))
                .andExpect(jsonPath("$[0].status").value("NEW"))
                .andExpect(jsonPath("$[0].deadline").value(new Date("2016/11/09 17:23").getTime()))
                .andExpect(jsonPath("$[0].taskText").value(""));
    }

    @Test
    public void testGetAllTasksByFilterAuthorId() throws Exception {
        mockMvc.perform(get("/task/filter")
                .param("authorId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].authorId").value(1))
                .andExpect(jsonPath("$[1].authorId").value(1));
    }

    @Test
    public void testGetAllTasksByFilterExecutorId() throws Exception {
        mockMvc.perform(get("/task/filter")
                .param("executorId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].executorId").value(2))
                .andExpect(jsonPath("$[1].executorId").value(2));
    }

    @Test
    public void testGetAllTasksByFilterStatus() throws Exception {
        mockMvc.perform(get("/task/filter")
                .param("status", "new"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].status").value("NEW"))
                .andExpect(jsonPath("$[1].status").value("NEW"));
    }

    @Test
    public void testGetAllTasksByFilterDeadline() throws Exception {
        mockMvc.perform(get("/task/filter")
                .param("deadline", "2016/11/07 17:23"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].deadline").value(new Date("2016/11/07 17:23").getTime()))
                .andExpect(jsonPath("$[1].deadline").value(new Date("2016/11/07 17:23").getTime()));
    }

    @Test
    public void testGetAllTasksByFilterAuthorIdAndStatus() throws Exception {
        mockMvc.perform(get("/task/filter")
                .param("authorId", "1")
                .param("status", "work"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        mockMvc.perform(get("/task/filter")
                .param("authorId", "2")
                .param("status", "work"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].authorId").value(2))
                .andExpect(jsonPath("$[0].status").value("WORK"));
    }

    @Test
    public void testGetAllTasksByFilterNoParam() throws Exception {
        mockMvc.perform(get("/task/filter"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void testGetAllTasksByFilterBadParamAuthorId() throws Exception {
        mockMvc.perform(get("/task/filter")
                .param("authorId", "22ff")
                .param("executorId", "2")
                .param("status", "new")
                .param("deadline", "2016/11/09 17:23"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllTasksByFilterBadParamExecutorId() throws Exception {
        mockMvc.perform(get("/task/filter")
                .param("authorId", "1")
                .param("executorId", "2gfg")
                .param("status", "new")
                .param("deadline", "2016/11/09 17:23"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllTasksByFilterBadParamStatus() throws Exception {
        mockMvc.perform(get("/task/filter")
                .param("authorId", "1")
                .param("executorId", "2")
                .param("status", "babad")
                .param("deadline", "2016/11/09 17:23"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllTasksByFilterBadParamDeadline() throws Exception {
        mockMvc.perform(get("/task/filter")
                .param("authorId", "1")
                .param("executorId", "2")
                .param("status", "new")
                .param("deadline", "999999ff"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetTaskAndComment() throws Exception {
        mockMvc.perform(get("/task/byId")
                .param("taskId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(2))
                .andExpect(jsonPath("$.authorId").value(1))
                .andExpect(jsonPath("$.taskTitle").value("title2"))
                .andExpect(jsonPath("$.taskText").value("text2"))
                .andExpect(jsonPath("$.comments.[0].taskId").value(2))
                .andExpect(jsonPath("$.comments.[0].authorId").value(1))
                .andExpect(jsonPath("$.comments.[0].commentText").value("comment1"));

        mockMvc.perform(get("/task/byId")
                .param("taskId", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(3))
                .andExpect(jsonPath("$.authorId").value(2))
                .andExpect(jsonPath("$.taskTitle").value("title3"))
                .andExpect(jsonPath("$.taskText").value("text3"))
                .andExpect(jsonPath("$.comments.length()").value(2))
                .andExpect(jsonPath("$.comments.[0].taskId").value(3))
                .andExpect(jsonPath("$.comments.[0].authorId").value(2))
                .andExpect(jsonPath("$.comments.[0].commentText").value("comment2"))
                .andExpect(jsonPath("$.comments.[1].taskId").value(3))
                .andExpect(jsonPath("$.comments.[1].authorId").value(2))
                .andExpect(jsonPath("$.comments.[1].commentText").value("comment3"));

        mockMvc.perform(get("/task/byId")
                .param("taskId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(1))
                .andExpect(jsonPath("$.authorId").value(1))
                .andExpect(jsonPath("$.taskTitle").value("title1"))
                .andExpect(jsonPath("$.taskText").value("text1"))
                .andExpect(jsonPath("$.comments.length()").value(0));

        mockMvc.perform(get("/task/byId")
                .param("taskId", "999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.task").doesNotExist());
    }

    @Test
    public void testGetTaskAndCommentBadParam() throws Exception {
        mockMvc.perform(get("/task/byId")
                .param("taskId", "ffdf"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddNewTask() throws Exception {
        mockMvc.perform(post("/task/new")
                .param("usedId", "1")
                .param("title", "titleee")
                .param("text", "texttt")
                .param("deadline", "2222/12/22 12:24")
                .param("executorId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorId").value("1"))
                .andExpect(jsonPath("$.status").value("NEW"))
                .andExpect(jsonPath("$.taskTitle").value("titleee"))
                .andExpect(jsonPath("$.taskText").value("texttt"))
                .andExpect(jsonPath("$.deadline").value(new Date("2222/12/22 12:24").getTime()))
                .andExpect(jsonPath("$.executorId").value(1));
    }

    @Test
    public void testAddNewTaskBadUsedId() throws Exception {
        mockMvc.perform(post("/task/new")
                .param("usedId", "1ff")
                .param("title", "titleee")
                .param("text", "texttt")
                .param("deadline", "2222/12/22 12:24")
                .param("executorId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddNewTaskBadExecutorId() throws Exception {
        mockMvc.perform(post("/task/new")
                .param("usedId", "1")
                .param("title", "titleee")
                .param("text", "texttt")
                .param("deadline", "2222/12/22 12:24")
                .param("executorId", "44ff"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddNewTaskBadDeadline() throws Exception {
        mockMvc.perform(post("/task/new")
                .param("usedId", "1")
                .param("title", "titleee")
                .param("text", "texttt")
                .param("deadline", "1231313")
                .param("executorId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddNewTaskTitleIsEmpty() throws Exception {
        mockMvc.perform(post("/task/new")
                .param("usedId", "1")
                .param("title", "")
                .param("text", "texttt")
                .param("deadline", "2222/12/22 12:24")
                .param("executorId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddNewTaskTextIsEmpty() throws Exception {
        mockMvc.perform(post("/task/new")
                .param("usedId", "1")
                .param("title", "titleee")
                .param("text", "")
                .param("deadline", "2222/12/22 12:24")
                .param("executorId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddNewTaskNoUsed() throws Exception {
        mockMvc.perform(post("/task/new")
                .param("usedId", "99")
                .param("title", "titleee")
                .param("text", "texttt")
                .param("deadline", "2222/12/22 12:24")
                .param("executorId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddNewTaskNoExecutor() throws Exception {
        mockMvc.perform(post("/task/new")
                .param("usedId", "1")
                .param("title", "titleee")
                .param("text", "texttt")
                .param("deadline", "2222/12/22 12:24")
                .param("executorId", "99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testEditTask() throws Exception {
        mockMvc.perform(post("/task/edit")
                .param("usedId", "1")
                .param("taskId", "1")
                .param("title", "titleee")
                .param("text", "texttt")
                .param("deadline", "2222/12/22 12:24")
                .param("executorId", "1")
                .param("status", "work"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorId").value("1"))
                .andExpect(jsonPath("$.status").value("WORK"))
                .andExpect(jsonPath("$.taskTitle").value("titleee"))
                .andExpect(jsonPath("$.taskText").value("texttt"))
                .andExpect(jsonPath("$.deadline").value(new Date("2222/12/22 12:24").getTime()))
                .andExpect(jsonPath("$.executorId").value(1));
    }

    @Test
    public void testEditTaskBadUsedId() throws Exception {
        mockMvc.perform(post("/task/edit")
                .param("usedId", "1ff")
                .param("taskId", "1")
                .param("title", "titleee")
                .param("text", "texttt")
                .param("deadline", "2222/12/22 12:24")
                .param("executorId", "1")
                .param("status", "work"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEditTaskBadTaskId() throws Exception {
        mockMvc.perform(post("/task/edit")
                .param("usedId", "1")
                .param("taskId", "fgf")
                .param("title", "titleee")
                .param("text", "texttt")
                .param("deadline", "2222/12/22 12:24")
                .param("executorId", "1")
                .param("status", "work"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEditTaskBadExecutorId() throws Exception {
        mockMvc.perform(post("/task/edit")
                .param("usedId", "1")
                .param("taskId", "1")
                .param("title", "titleee")
                .param("text", "texttt")
                .param("deadline", "2222/12/22 12:24")
                .param("executorId", "44ff")
                .param("status", "work"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEditTaskBadDeadline() throws Exception {
        mockMvc.perform(post("/task/edit")
                .param("usedId", "1")
                .param("taskId", "1")
                .param("title", "titleee")
                .param("text", "texttt")
                .param("deadline", "1231313")
                .param("executorId", "1")
                .param("status", "work"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEditTaskNoUsed() throws Exception {
        mockMvc.perform(post("/task/edit")
                .param("usedId", "99")
                .param("taskId", "1")
                .param("title", "titleee")
                .param("text", "texttt")
                .param("deadline", "2222/12/22 12:24")
                .param("executorId", "1")
                .param("status", "work"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testEditTaskNoTask() throws Exception {
        mockMvc.perform(post("/task/edit")
                .param("usedId", "1")
                .param("taskId", "99")
                .param("title", "titleee")
                .param("text", "texttt")
                .param("deadline", "2222/12/22 12:24")
                .param("executorId", "1")
                .param("status", "work"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testEditTaskNoExecutor() throws Exception {
        mockMvc.perform(post("/task/edit")
                .param("usedId", "1")
                .param("taskId", "1")
                .param("title", "titleee")
                .param("text", "texttt")
                .param("deadline", "2222/12/22 12:24")
                .param("executorId", "99")
                .param("status", "work"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testEditTaskBadStatus() throws Exception {
        mockMvc.perform(post("/task/edit")
                .param("usedId", "1")
                .param("taskId", "1")
                .param("title", "titleee")
                .param("text", "texttt")
                .param("deadline", "2222/12/22 12:24")
                .param("executorId", "1")
                .param("status", "bobi"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEditTaskNoParamTextExecutorStatus() throws Exception {
        mockMvc.perform(post("/task/edit")
                .param("usedId", "1")
                .param("taskId", "1")
                .param("title", "titleee")
                .param("deadline", "2222/12/22 12:24"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorId").value("1"))
                .andExpect(jsonPath("$.taskId").value("1"))
                .andExpect(jsonPath("$.taskTitle").value("titleee"))
                .andExpect(jsonPath("$.taskText").value("text1"))
                .andExpect(jsonPath("$.deadline").value(new Date("2222/12/22 12:24").getTime()));
    }

    @Test
    public void testEditTaskNoParamTitleDeadline() throws Exception {
        mockMvc.perform(post("/task/edit")
                .param("usedId", "1")
                .param("taskId", "1")
                .param("text", "titleee")
                .param("executorId", "2")
                .param("status", "made"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorId").value("1"))
                .andExpect(jsonPath("$.taskId").value("1"))
                .andExpect(jsonPath("$.taskTitle").value("title1"))
                .andExpect(jsonPath("$.taskText").value("titleee"))
                .andExpect(jsonPath("$.executorId").value("2"))
                .andExpect(jsonPath("$.status").value("MADE"));
    }

    @Test
    public void testEditTaskNoParamUsedId() throws Exception {
        mockMvc.perform(post("/task/edit")
                .param("taskId", "1")
                .param("title", "titleee")
                .param("deadline", "2222/12/22 12:24"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testEditTaskNoParamTaskId() throws Exception {
        mockMvc.perform(post("/task/edit")
                .param("usedId", "1")
                .param("title", "titleee")
                .param("deadline", "2222/12/22 12:24"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddComment() throws Exception {
        mockMvc.perform(post("/comment/new")
                .param("usedId", "1")
                .param("text", "text")
                .param("taskId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value(4))
                .andExpect(jsonPath("$.taskId").value(1))
                .andExpect(jsonPath("$.authorId").value(1))
                .andExpect(jsonPath("$.commentText").value("text"));

        mockMvc.perform(post("/comment/new")
                .param("usedId", "1")
                .param("text", "text")
                .param("taskId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value(5))
                .andExpect(jsonPath("$.taskId").value(1))
                .andExpect(jsonPath("$.authorId").value(1))
                .andExpect(jsonPath("$.commentText").value("text"));
    }

    @Test
    public void testAddCommentBadUsed() throws Exception {
        mockMvc.perform(post("/comment/new")
                .param("usedId", "gfgf")
                .param("text", "text")
                .param("taskId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddCommentBadTask() throws Exception {
        mockMvc.perform(post("/comment/new")
                .param("usedId", "1")
                .param("text", "text")
                .param("taskId", "fgfg"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddCommentTextIsEmpty() throws Exception {
        mockMvc.perform(post("/comment/new")
                .param("usedId", "1")
                .param("text", "")
                .param("taskId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddCommentNoUsed() throws Exception {
        mockMvc.perform(post("/comment/new")
                .param("usedId", "99")
                .param("text", "text")
                .param("taskId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddCommentNoTask() throws Exception {
        mockMvc.perform(post("/comment/new")
                .param("usedId", "1")
                .param("text", "text")
                .param("taskId", "99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddCommentIsComment() throws Exception {
        mockMvc.perform(post("/comment/new")
                .param("usedId", "1")
                .param("text", "text")
                .param("taskId", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value(4))
                .andExpect(jsonPath("$.taskId").value(2))
                .andExpect(jsonPath("$.commentText").value("text"))
                .andExpect(jsonPath("$.authorId").value(1))
        ;
    }
}