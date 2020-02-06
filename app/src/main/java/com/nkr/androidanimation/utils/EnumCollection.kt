package com.nkr.androidanimation.utils

/**
 * home page enum
 */
enum class PostDetailType {
    CATEGORIES,
    POST_DETAIL
}

/**
 * category selection page enum
 */
enum class CategoryViewType {
    SIGN_UP,
    UPDATE,
    UPDATE_INTERESTED_IN,
    VIEW,
    FILTER,
    FILTER_POST,
    FRIEND_CATEGORIES_VIEW,
    POST_CREATION,
    POST_MODIFY
}

/**
 * sub category select case
 */
enum class SubCategorySelect {
    NONE,
    HIM,
    HER,
    BOTH
}

/**
 * Profile Type
 */
enum class ProfileViewType {
    OWN_SHOW_BACK,
    OWN_NON_SHOW_BACK,
    OTHER_SHOW_BACK,
}

/**
 * Post Type
 */
enum class PostType {
    SAME_PERSON_POST,
    SUGGEST_POST,
    SEARCH_POST,
    HOME_POST,
    OWN_POST
}

/**
 * use for notification click
 */
enum class PostTypeViewAction {
    NONE,
    VIEW_LOVE_LIST,
    VIEW_CHEER_LIST,
    VIEW_COMMENT_LIST
}


/**
 * Post creation method
 */
enum class PostCreationMethod {
    CAMERA,
    GALLERY,
}

/**
 * report or feedback type
 */
enum class ReportType {
    REPORT,
    FEEDBACK
}

/**
 * post gesture action type
 */
enum class GestureActionType {
    SWIPE_UP,
    LONG_CLICK,
    CLICK
}


/**
 * post gesture action type
 */
enum class EmojiType {
    JOY,
    CRYING,
    BLUSHING,
    THINKING,
    PRAYING,
    HEARTEYES
}

/**
 * post gesture action type
 */
enum class PostMethod {
    EMOJI,
    TITLE_SUBTITLE
}