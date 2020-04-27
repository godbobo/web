CREATE TABLE IF NOT EXISTS blog_tags_map (
    tag_id     bigint(20) NOT NULL COMMENT '标签主键',
    article_id bigint(20) NOT NULL COMMENT '文章主键',
    primary key (tag_id, article_id)
);$$