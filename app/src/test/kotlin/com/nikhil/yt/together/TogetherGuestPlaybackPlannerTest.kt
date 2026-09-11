package com.nikhil.yt.together

import org.junit.Assert.assertEquals
import org.junit.Test

class TogetherGuestPlaybackPlannerTest {
    @Test
    fun planPlayTrackNow_returnsEmpty_whenControlDisabled() {
        val roomState =
            TogetherRoomState(
                sessionId = "sid",
                hostId = "hid",
                settings = TogetherRoomSettings(allowGuestsToControlPlayback = false, allowGuestsToAddTracks = true),
                queue = listOf(TogetherTrack(id = "a", title = "A")),
                currentIndex = 0,
                isPlaying = true,
            )
        val ops = TogetherGuestPlaybackPlanner.planPlayTrackNow(roomState, TogetherTrack(id = "a", title = "A"), 0L, true)
        assertEquals(emptyList<TogetherGuestOp>(), ops)
    }

    @Test
    fun planPlayTrackNow_seeks_whenTrackExistsInHostQueue() {
        val roomState =
            TogetherRoomState(
                sessionId = "sid",
                hostId = "hid",
                settings = TogetherRoomSettings(allowGuestsToControlPlayback = true, allowGuestsToAddTracks = false),
                queue = listOf(TogetherTrack(id = "a", title = "A"), TogetherTrack(id = "b", title = "B")),
                currentIndex = 0,
                isPlaying = true,
            )
        val ops = TogetherGuestPlaybackPlanner.planPlayTrackNow(roomState, TogetherTrack(id = "b", title = "B"), 123L, true)
        assertEquals(
            listOf(
                TogetherGuestOp.Control(ControlAction.SeekToTrack(trackId = "b", positionMs = 123L)),
            ),
            ops,
        )
    }

    @Test
    fun planPlayTrackNow_addsAndSelectsRequestedTrack_whenTrackMissingAndAddAllowed() {
        val roomState =
            TogetherRoomState(
                sessionId = "sid",
                hostId = "hid",
                settings = TogetherRoomSettings(allowGuestsToControlPlayback = true, allowGuestsToAddTracks = true),
                queue = listOf(TogetherTrack(id = "a", title = "A")),
                currentIndex = 0,
                isPlaying = true,
            )
        val ops = TogetherGuestPlaybackPlanner.planPlayTrackNow(roomState, TogetherTrack(id = "b", title = "B"), 0L, true)
        assertEquals(
            listOf(
                TogetherGuestOp.AddTrack(TogetherTrack(id = "b", title = "B"), AddTrackMode.PLAY_NEXT),
                // Select by ID: SkipNext could select the wrong track if the host queue changes.
                TogetherGuestOp.Control(ControlAction.SeekToTrack(trackId = "b", positionMs = 0L)),
            ),
            ops,
        )
    }

    @Test
    fun planPlayTrackNow_requestsPlay_whenHostPausedAndPlayWhenReadyTrue() {
        val roomState =
            TogetherRoomState(
                sessionId = "sid",
                hostId = "hid",
                settings = TogetherRoomSettings(allowGuestsToControlPlayback = true, allowGuestsToAddTracks = false),
                queue = listOf(TogetherTrack(id = "a", title = "A")),
                currentIndex = 0,
                isPlaying = false,
            )
        val ops = TogetherGuestPlaybackPlanner.planPlayTrackNow(roomState, TogetherTrack(id = "a", title = "A"), 0L, true)
        assertEquals(
            listOf(
                TogetherGuestOp.Control(ControlAction.SeekToTrack(trackId = "a", positionMs = 0L)),
                TogetherGuestOp.Control(ControlAction.Play),
            ),
            ops,
        )
    }

    private fun room(allowAdd: Boolean = true) = TogetherRoomState(
        sessionId = "sid",
        hostId = "hid",
        settings = TogetherRoomSettings(allowGuestsToControlPlayback = true, allowGuestsToAddTracks = allowAdd),
        queue = listOf(TogetherTrack(id = "a", title = "A")),
        currentIndex = 0,
        isPlaying = false,
    )

    @Test
    fun planPlayTrackNow_rejectsBlankTrackId() {
        assertEquals(emptyList<TogetherGuestOp>(), TogetherGuestPlaybackPlanner.planPlayTrackNow(
            room(), TogetherTrack(id = "  ", title = "Invalid"), 0L, true,
        ))
    }

    @Test
    fun planPlayTrackNow_rejectsMissingTrackWhenAddingDisabled() {
        assertEquals(emptyList<TogetherGuestOp>(), TogetherGuestPlaybackPlanner.planPlayTrackNow(
            room(allowAdd = false), TogetherTrack(id = "b", title = "B"), 0L, true,
        ))
    }

    @Test
    fun planPlayTrackNow_trimsIdClampsPositionAndKeepsPaused() {
        assertEquals(
            listOf(TogetherGuestOp.Control(ControlAction.SeekToTrack(trackId = "a", positionMs = 0L))),
            TogetherGuestPlaybackPlanner.planPlayTrackNow(
                room(), TogetherTrack(id = " a ", title = "A"), -100L, false,
            ),
        )
    }

    @Test
    fun planPlayTrackNow_addsSelectsAndPlaysWhenHostPaused() {
        assertEquals(
            listOf(
                TogetherGuestOp.AddTrack(TogetherTrack(id = "b", title = "B"), AddTrackMode.PLAY_NEXT),
                TogetherGuestOp.Control(ControlAction.SeekToTrack(trackId = "b", positionMs = 0L)),
                TogetherGuestOp.Control(ControlAction.Play),
            ),
            TogetherGuestPlaybackPlanner.planPlayTrackNow(
                room(), TogetherTrack(id = " b ", title = "B"), 500L, true,
            ),
        )
    }
}
