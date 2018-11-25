package com.pandulapeter.campfire.feature.main.home.onboarding.userData

import com.pandulapeter.campfire.R
import com.pandulapeter.campfire.databinding.FragmentOnboardingUserDataBinding
import com.pandulapeter.campfire.feature.shared.CampfireFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDataFragment : CampfireFragment<FragmentOnboardingUserDataBinding, UserDataViewModel>(R.layout.fragment_onboarding_user_data) {

    override val viewModel by viewModel<UserDataViewModel>()
}