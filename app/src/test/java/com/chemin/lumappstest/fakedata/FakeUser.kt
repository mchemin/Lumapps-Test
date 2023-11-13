package com.chemin.lumappstest.fakedata

import com.chemin.lumappstest.domain.model.PictureUrl
import com.chemin.lumappstest.domain.model.SimpleDataUser
import com.chemin.lumappstest.domain.model.UserId
import com.chemin.lumappstest.domain.model.UserName

val FakeId = "105L"
val FakeTitle = "Mr."
val FakeFirstName = "Matthieu"
val FakeLastName = "Chemin"
val FakeEmail = "matthieu@example.com"
val FakeSmallUrl = "small.url"
val FakeLargeUrl = "large.url"
val ExpectedDisplayName = "Mr. Matthieu Chemin"

val FakeSimpleDataUser = SimpleDataUser(
    id = UserId(FakeId),
    name = UserName(
        title = FakeTitle,
        first = FakeFirstName,
        last = FakeLastName,
    ),
    email = FakeEmail,
    pictureUrl = PictureUrl(
        thumbnail = FakeSmallUrl,
        large = FakeLargeUrl,
    )
)
