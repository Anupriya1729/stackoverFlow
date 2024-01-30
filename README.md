Problem Statement Given:
Design and implement a competitor to Stack Overflow Website.

http://localhost:8080/swagger-ui/index.html#/

**Functional Requirements**:

1. User can create profile
2. User can Post a question: text+(photos/videos)
3. User can answer a question: text+(photos/videos)
4. User can add tag to a question 
5. User can upvote or downvote a question 
6. User can upvote or downvote an answer
7. User can add comments to an answer/question: text
8. User can browse question by a tag. Example: if we type [tagName] on search bar, 
    all questions under that tag will be displayed.
    (TagName should not have spaces, in case of spaces system removes the spaces and allows search)
9. User can search by text
   The search is performed on content of question/answer
10. Everyone (Users+Non-users) should be able to view the Top questions on home page
    Question priority is calculate by: most recent one first


**Assumptions**:
- Any userInfo can upvote,downvote - reputation of userInfo not required
- Multimedia Content: Questions and answers both can include photos/videos.
- There is no particular Accepted answer
- Voting: Users can upvote or downvote both questions and answers (but not comments)
    once upvoted, if userInfo downvotes the same, the upvote gets cancelled.
    and upvote/ downvote only once
- Comments: Users can add comments to both questions and answers.
- Tag System: Tags cannot have spaces, and the system removes spaces for searches.
- Homepage Ranking: Question priority is calculated based on the number of upvotes
  In case of a tie, the most recent question is displayed first


---------------------------------------------------------------------------------------------------------------------------------------------
