# SwipeRefreshLayout issues when child is gone or invisible
A minimal example of an issue with SwipeRefreshLayout when the child ListView, RecyclerView or other scrollable view's visibility is set to gone or invisible

The following describes an issue with android.support.v4.widget.SwipeRefreshLayout from support-v4-23.4.0

Often a ListView or RecyclerView is not sufficient to show a user all information. It must be augmented with empty-views, loading-views and error-views. It is in these circumstances that an app developer may want to toggle the visibility of the list-view. (You could understand there may be items in the list/adapter but the developer may still need to show the error-view). Setting the ListView or RecyclerView's visibility to gone or invisible causes an issue when it is the child of a SwipeRefreshLayout. The SwipeRefreshLayout will not appear during initial drag. It should appear. Upon releasing the drag the SwipeRefreshLayout will appear and animate into position. The problem here is that the arrow animation does not appear. Once it reaches its resting position where it would usually remain until setRefreshing(false) is called, it disappears. Expected behaviour is that it remains in view and rotates to indicate the list is refreshing.

Curiously, at this point it is possible to interact with the SwipeRefreshLayout once more. If you then drag upwards a large distance and then release, the SwipeRefreshLayout will appear and animate off of the screen more-or-less glitch free.

Usually at this point the SwipeRefreshLayout will not respond to gestures from this point, but some strange glitching is possible with the right sequence of clicks/drags.

## ListView

ListView has some subtle differences. 

1. A minor issue is the behaviour difference when no adapter is set. RecyclerView requires an adapter to be set for SwipeRefresh to work. ListView does not.

2. More importantly, IMHO ListView doesn't mind if the first view has a height of zero. SwipeRefreshLayout doesn't work with RecyclerView with a first itemView of height 0. This is useful when loading multiple data sources when you want the RecyclerView to stay at the top of the list even when data is added at the top. This is a frequent occurence when loading from multiple sources to be shown in one RecyclerView. If we load 10 items from one API endpoint and another 10 from another, we may show a progressBar for each and either may complete first. However depending on which finishes first, the RecyclerView may scroll based on which items are added or removed first. It would be convenient if SwipeRefreshLayout behaved the same as ListView in this respect.

## ScrollView

If you change each ListView to a ScrollView you will notice the same issues in addition to the issue presenting itself on visible ScrollViews with no children.
