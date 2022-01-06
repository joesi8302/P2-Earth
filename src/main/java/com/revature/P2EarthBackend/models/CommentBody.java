package com.revature.P2EarthBackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentBody {
    private String comment_description;
    private Long post_id;

}
