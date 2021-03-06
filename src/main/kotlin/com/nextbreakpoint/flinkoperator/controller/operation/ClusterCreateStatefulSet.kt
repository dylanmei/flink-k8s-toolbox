package com.nextbreakpoint.flinkoperator.controller.operation

import com.nextbreakpoint.flinkoperator.common.model.ClusterId
import com.nextbreakpoint.flinkoperator.common.model.FlinkOptions
import com.nextbreakpoint.flinkoperator.common.utils.FlinkClient
import com.nextbreakpoint.flinkoperator.common.utils.KubeClient
import com.nextbreakpoint.flinkoperator.controller.core.Operation
import com.nextbreakpoint.flinkoperator.controller.core.OperationResult
import com.nextbreakpoint.flinkoperator.controller.core.OperationStatus
import io.kubernetes.client.models.V1StatefulSet
import org.apache.log4j.Logger

class ClusterCreateStatefulSet(flinkOptions: FlinkOptions, flinkClient: FlinkClient, kubeClient: KubeClient) : Operation<V1StatefulSet, String?>(flinkOptions, flinkClient, kubeClient) {
    companion object {
        private val logger = Logger.getLogger(ClusterCreateStatefulSet::class.simpleName)
    }

    override fun execute(clusterId: ClusterId, params: V1StatefulSet): OperationResult<String?> {
        try {
            val statefuleSetOut = kubeClient.createStatefulSet(clusterId, params)

            return OperationResult(
                OperationStatus.COMPLETED,
                statefuleSetOut.metadata.name
            )
        } catch (e : Exception) {
            logger.error("[name=${clusterId.name}] Can't create resources", e)

            return OperationResult(
                OperationStatus.FAILED,
                null
            )
        }
    }
}