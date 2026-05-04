package com.currix.distributed_lovable.account_service.service;

import com.currix.distributed_lovable.account_service.dto.subscription.SubscriptionResponse;
import com.currix.distributed_lovable.common_lib.dto.PlanDto;
import com.currix.distributed_lovable.common_lib.enums.SubscriptionStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public interface SubscriptionService {
    SubscriptionResponse getCurrentSubscription();

    void activateSubscription(Long userId, Long planId, String subscriptionId);

    void updateSubscription(String gatewaySubscriptionId, SubscriptionStatus status, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId);

    void cancelSubscription(String gatewaySubscriptionId);

    void renewSubscriptionPeriod(String subId, Instant periodStart, Instant periodEnd);

    void markSubscriptionPastDue(String subId);

    PlanDto getCurrentSubscribedPlanByUser();
}
