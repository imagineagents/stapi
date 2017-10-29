package com.cezarykluczynski.stapi.sources.oauth.github.session

import com.google.common.collect.Lists
import spock.lang.Specification

class OAuthSessionHolderTest extends Specification {

	private static final Long ID = 11L
	private static final String NAME = 'NAME'
	private static final String PERMISSION = 'PERMISSION'

	private OAuthSessionHolder oAuthSessionHolder

	void setup() {
		oAuthSessionHolder = new OAuthSessionHolder()
	}

	void "set OAuthSession, then returns copy of it"() {
		given:
		OAuthSession oAuthSession = new OAuthSession(gitHubId: ID, gitHubName: NAME, permissions: Lists.newArrayList(PERMISSION))
		oAuthSessionHolder.setOAuthSession(oAuthSession)

		when:
		OAuthSession oAuthSessionCopy = oAuthSessionHolder.OAuthSession

		then:
		oAuthSessionCopy.gitHubId == oAuthSession.gitHubId
		oAuthSessionCopy.gitHubName == oAuthSession.gitHubName
		oAuthSessionCopy.permissions[0] == oAuthSession.permissions[0]

		when:
		oAuthSession.gitHubId = null
		oAuthSession.gitHubName = null
		oAuthSession.permissions = Lists.newArrayList()

		then:
		oAuthSession.gitHubId == null
		oAuthSession.gitHubName == null
		oAuthSession.permissions == Lists.newArrayList()

		then:
		oAuthSessionCopy.gitHubId == ID
		oAuthSessionCopy.gitHubName == NAME
		oAuthSessionCopy.permissions[0] == PERMISSION
	}

	void "returns null when no OAuthSession has been set"() {
		when:
		OAuthSession oAuthSessionCopy = oAuthSessionHolder.OAuthSession

		then:
		oAuthSessionCopy == null
	}

}