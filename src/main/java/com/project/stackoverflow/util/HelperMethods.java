package com.project.stackoverflow.util;

import com.project.stackoverflow.constant.VoteType;
import com.project.stackoverflow.entity.Post;

public class HelperMethods {
    public static int countVotes(Post post, VoteType voteType) {
        if (post.getVotes() == null) {
            return 0;
        }

        return (int) post.getVotes().stream()
                .filter(vote -> vote.getVoteType() == voteType)
                .count();
    }
}
